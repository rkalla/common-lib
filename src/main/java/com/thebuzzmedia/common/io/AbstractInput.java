/**   
 * Copyright 2011 The Buzz Media, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thebuzzmedia.common.io;

import java.io.IOException;

public abstract class AbstractInput<ST, BT> implements IInput<ST, BT> {
	/**
	 * Position within the wrapped <code>source</code> where the first read
	 * operation will begin.
	 * <p/>
	 * Set by the user when the input is created. A value of
	 * {@link IInput#UNSPECIFIED} indicates that no index is set or using one is
	 * not supported by this {@link IInput} implementation.
	 */
	protected int sIndex;

	/**
	 * Maximum limit on the amount of data that can be read from the wrapped
	 * <code>source</code> before the input is considered empty.
	 * <p/>
	 * Set by the user when the input is created. A value of
	 * {@link IInput#UNSPECIFIED} indicates that no length was set or using one
	 * is not supported by this {@link IInput} implementation.
	 */
	protected int maxLength;

	/**
	 * Internal index used to track the beginning position of the next read
	 * operation. Each time a successful read operation is completed, the length
	 * of data read is used to increment this value.
	 * <p/>
	 * An input is considered empty once this value is &gt;=
	 * <code>(sIndex + maxLength)</code> or if <code>sourceEmpty</code> is
	 * <code>true</code>.
	 * <p/>
	 * For {@link IInput} implementations that don't need this value, it's value
	 * will start at <code>0</code> and increment after each successful read
	 * operation; effectively keeping track of the amount of data read from the
	 * underlying <code>source</code>.
	 */
	protected int position;

	/**
	 * Convenience value used to store the pre-computed value of
	 * <code>sIndex + maxLength</code>, making remaining and end calculations
	 * faster (so the addition doesn't need to be done each time).
	 */
	protected int endPosition;

	/**
	 * The wrapped source we get data from.
	 */
	protected ST source;

	/**
	 * Flag used to indicate when the underlying source is empty. This is one of
	 * our isEmpty conditions.
	 */
	protected boolean sourceEmpty;

	public AbstractInput(ST source, int index, int length)
			throws IllegalArgumentException {
		if (source == null)
			throw new IllegalArgumentException("source cannot be null");
		if (index < UNSPECIFIED)
			throw new IllegalArgumentException(
					"index ["
							+ index
							+ "] must be "
							+ UNSPECIFIED
							+ " (unspecified) or a valid position in source that is >= 0");
		if (length < UNSPECIFIED)
			throw new IllegalArgumentException(
					"length ["
							+ length
							+ "] must be "
							+ UNSPECIFIED
							+ " (unspecified) or a valid length of data from source that is >= 0");

		this.sIndex = index;
		this.maxLength = length;

		this.position = 0;

		/*
		 * End position will either be index+length, or just length if index is
		 * UNSPECIFIED; automatically making endPosition UNSPECIFIED if length
		 * was UNSPECIFIED as well (0 + -1).
		 */
		this.endPosition = ((this.sIndex == UNSPECIFIED ? 0 : this.sIndex) + this.maxLength);

		this.source = source;
		this.sourceEmpty = false;
	}

	public ST getSource() {
		return source;
	}

	public boolean isEmpty() {
		/*
		 * We are empty if our source was empty after a previous read op or if
		 * we have a limit set on our length and our current position is >= to
		 * it.
		 */
		return (sourceEmpty || (endPosition != UNSPECIFIED && position >= endPosition));
	}

	public int getIndex() {
		return sIndex;
	}

	public int getLength() {
		return maxLength;
	}

	public int getPosition() {
		return position;
	}

	public int getRemaining() {
		// Only return a known value if we know our ending point.
		return (endPosition == UNSPECIFIED ? UNSPECIFIED : endPosition
				- position);
	}

	public int read(BT buffer) throws IllegalArgumentException, IOException {
		if (buffer == null)
			throw new IllegalArgumentException("buffer cannot be null");

		return read(buffer, 0);
	}

	public int read(BT buffer, int index, int length)
			throws IllegalArgumentException, IOException {
		if (buffer == null)
			throw new IllegalArgumentException("buffer cannot be null");
		if (index < 0 || length < 0)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0 and length [" + length + "] must be >= 0");

		return readInt(buffer, index, length);
	}

	/**
	 * Internal read operation that all other read operations funnel into and
	 * does the following:
	 * <ol>
	 * <li>If sIndex/maxLength bounds are set on this input, the given length
	 * argument is trimmed down to fit within {@link #getRemaining()} (if
	 * needed).</li>
	 * <li>If the data read by {@link #readImpl(Object, int, int)} is 0, the
	 * <code>sourceEmpty</code> flag is set to <code>true</code>.</li>
	 * <li>Updates the value of <code>position</code> by the amount of data read
	 * from <code>source</code>.</li>
	 * </ol>
	 * 
	 * @param buffer
	 *            The read buffer that will have content from
	 *            <code>source</code> written into it.
	 * @param index
	 *            The position in the given buffer where new content will begin
	 *            being written.
	 * @param length
	 *            The maximum amount (maybe less) of data to read from
	 *            <code>source</code> and place into the given buffer.
	 * 
	 * @return the amount of data read from the underlying <code>source</code>.
	 */
	protected int readInt(BT buffer, int index, int length)
			throws IllegalArgumentException, IOException {
		// Check if we have nothing to do first.
		if (isEmpty())
			return 0;

		int remaining = getRemaining();

		// Trim the length of the read if supported and necessary.
		if (remaining != UNSPECIFIED)
			length = (length > remaining ? remaining : length);

		// Do the read op (re-use length variable)
		length = readImpl(buffer, index, length);

		// Check if source is empty
		if (length == 0)
			sourceEmpty = true;

		// Update the position
		position += length;

		// Return the amount of data read to caller
		return length;
	}

	/**
	 * Used to perform the actual "read" logic based on the specific type of
	 * <code>source</code> and <code>buffer</code> this {@link IInput} supports.
	 * <p/>
	 * This operation should read <em>up to</em> <code>length</code> amount of
	 * data from <code>source</code>, placing it into the buffer at the given
	 * <code>index</code> and returning the final amount of data read from the
	 * <code>source</code>.
	 * <p/>
	 * It is possible that less than <code>length</code> data is read and
	 * written into the buffer.
	 * <p/>
	 * A return value of <code>0</code> indicates that the <code>source</code>
	 * has been exhausted (empty) and this {@link IInput} can now be considered
	 * "empty". Whatever remaining data it had is already contained within the
	 * buffer and all future <code>read</code> operations will return nothing.
	 * <p/>
	 * Two things can cause this method to read less data than requested by
	 * <code>length</code>:
	 * <ol>
	 * <li>The underlying <code>source</code> ran out of data before
	 * <code>length</code> amount of data was read. (e.g. stream)</li>
	 * <li>The amount of data remaining within this input (
	 * {@link #getRemaining()}), is less than <code>length</code>.</li>
	 * </ol>
	 * In the case of #1, the underlying <code>source</code> implementation will
	 * automatically limit the read data (e.g. streams). In the case of #2, the
	 * specific {@link IInput} implementation needs to enforce the limitation on
	 * the amount of data read to match {@link #getRemaining()}.
	 * <p/>
	 * <strong>NOTE</strong>: By the time this method is called, the values for
	 * {@link #getSource()} and <code>buffer</code> have been confirmed to be
	 * non-<code>null</code>. Also, <code>index</code> and <code>length</code>
	 * have been confirmed to be &gt;= 0.
	 * <p/>
	 * The only verification left for any implementation to do is ensure that
	 * <code>index</code> and <code>length</code> are valid bounds within the
	 * given <code>buffer</code>.
	 * 
	 * @param buffer
	 *            The destination for data copied from the <code>source</code>.
	 * @param index
	 *            The position in the buffer where new data from the
	 *            <code>source</code> will start to be written.
	 * @param length
	 *            The requested amount of data to be read from
	 *            <code>source</code> and written to the buffer (could end up
	 *            being less).
	 * 
	 * @return the amount of data read from the <code>source</code> and written
	 *         into the buffer. Returning a value of <code>0</code> indicates
	 *         that the <code>source</code> has been exhausted (empty) and has
	 *         no more data that can be read from it.
	 * 
	 * @throws IllegalArgumentException
	 *             if (<code>index+length</code>) &gt; the length of the buffer.
	 * @throws IOException
	 *             if any problem arises with reading content from the
	 *             underlying <code>source</code>.
	 */
	protected abstract int readImpl(BT buffer, int index, int length)
			throws IllegalArgumentException, IOException;
}