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
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @param <ST>
 *            The type of the source that this {@link IInput} is wrapping; for
 *            example {@link InputStream}, {@link ByteBuffer}, etc.
 * @param <BT>
 *            The type of the primitive read buffer that will contain data read
 *            from the given source. This is typically either
 *            <code>byte[]</code> or <code>char[]</code> (for reading from
 *            binary or character-based sources), but it could easily be any
 *            other type you want to implement.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 * @since 2.2
 */
public interface IInput<ST, BT> {
	public static final int UNSPECIFIED = -1;

	/**
	 * Used to determine if this input is empty. An {@link IInput} is empty when
	 * it has no more data to provide for <code>read</code> operations.
	 * <p/>
	 * The "emptiness" of an input is determined by one of two things:
	 * <ol>
	 * <li>During a previous <code>read</code> operation, the underlying
	 * <code>source</code> returned no data or indicated that it was empty (e.g.
	 * a stream returning -1).</li>
	 * <li>The read-bounds (if supported) imposed on the {@link IInput} by a
	 * given starting <code>index</code> and <code>length</code> were hit,
	 * resulting in {@link #getRemaining()} to return <code>0</code>.</li>
	 * </ol>
	 * These are the only two conditions that can trigger an input to be
	 * considered empty.
	 * 
	 * @return <code>true</code> if the input is empty and future <code>read
	 *         </code> operations will return nothing, otherwise returns <code>
	 *         false</code>.
	 */
	public boolean isEmpty();

	/**
	 * Used to get the bounded start-position where this {@link IInput} will
	 * begin its first <code>read</code> operation or {@link #UNSPECIFIED} if no
	 * position was specified or specifying one is not supported by this
	 * {@link IInput} implementation.
	 * <p/>
	 * An example of an input that would support using an <code>index</code>
	 * might wrap an array or other in-memory data representation where the
	 * bounds are known. An example of an input that would not support using an
	 * <code>index</code> might be an input that wraps network streams, where
	 * the final length of in-bound data is unknown.
	 * 
	 * @return the bounded start-position where this {@link IInput} will begin
	 *         its first <code>read</code> operation or {@link #UNSPECIFIED} if
	 *         no position was specified or specifying one is not supported by
	 *         this {@link IInput} implementation.
	 */
	public int getIndex();

	/**
	 * Used to get the limit on the amount of data that can be read from this
	 * input or {@link #UNSPECIFIED} if no limit is specified or supported.
	 * <p/>
	 * This value <em>does not</em> necessarily reflect the length of the
	 * underlying <code>source</code>, but rather a cap on the amount of data
	 * that can be read out of the underlying source (as enforced by the
	 * particular {@link IInput} implementation).
	 * <p/>
	 * It is possible that the underlying <code>source</code> has less data
	 * available than this value, especially if the <code>source</code> has no
	 * way of knowing in-advance how much data it contains, but the amount of
	 * data read from this input should <strong>never be more</strong> than this
	 * amount.
	 * 
	 * @return the limit on the amount of data that can be read from this input
	 *         or {@link #UNSPECIFIED} if no limit is specified or supported.
	 */
	public int getLength();

	/**
	 * Used to get the position within <code>source</code> where the next
	 * <code>read</code> operation will begin.
	 * <p/>
	 * This value should <strong>always</strong> be &gt;= 0.
	 * <p/>
	 * {@link IInput} implementations should update this value after each
	 * successful <code>read</code> operation, incrementing it by the amount of
	 * data read each time.
	 * <p/>
	 * For {@link IInput} implementations that are unbounded or have an unknown
	 * length, this value ends up being a running total of the amount of data
	 * read from the underlying <code>source</code>.
	 * <p/>
	 * An {@link IInput} is considered empty when {@link #getPosition()} ==
	 * {@link #getIndex()} + {@link #getLength()}.
	 * 
	 * @return the position within <code>source</code> where the next
	 *         <code>read</code> operation will begin.
	 */
	public int getPosition();

	/**
	 * Used to get the amount of data remaining that can be read from the
	 * underlying <code>source</code> or {@link #UNSPECIFIED} to indicate that
	 * the amount of remaining data is unknown.
	 * <p/>
	 * It is recommended that callers use {@link #isEmpty()} to check if an
	 * {@link IInput} is empty instead of checking this value, because not all
	 * {@link IInput} implementations will be able to determine how much data is
	 * remaining from their underlying <code>source</code>.
	 * 
	 * @return the amount of data remaining that can be read from the underlying
	 *         <code>source</code> or {@link #UNSPECIFIED} to indicate that the
	 *         amount of remaining data is unknown.
	 */
	public int getRemaining();

	/**
	 * Used to get the underlying source that data is pulled from.
	 * 
	 * @return the underlying source that data is pulled from.
	 */
	public ST getSource();

	public int read(BT buffer) throws IllegalArgumentException, IOException;

	public int read(BT buffer, int index) throws IllegalArgumentException,
			IOException;

	public int read(BT buffer, int index, int length)
			throws IllegalArgumentException, IOException;
}