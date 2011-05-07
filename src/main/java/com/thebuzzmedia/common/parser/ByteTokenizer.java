package com.thebuzzmedia.common.parser;

import com.thebuzzmedia.common.util.ArrayUtils;

public class ByteTokenizer extends AbstractTokenizer<byte[]> {

	public void setSource(byte[] source, byte[] delimiters)
			throws IllegalArgumentException {
		if (source == null)
			throw new IllegalArgumentException("source cannot be null");

		setSource(source, delimiters, DelimiterMatchType.ANY, 0, source.length);
	}

	public void setSource(byte[] source, byte[] delimiters,
			DelimiterMatchType delimiterMatchType)
			throws IllegalArgumentException {
		if (source == null)
			throw new IllegalArgumentException("source cannot be null");

		setSource(source, delimiters, delimiterMatchType, 0, source.length);
	}

	public void setSource(byte[] source, byte[] delimiters,
			DelimiterMatchType delimiterMatchType, int index)
			throws IllegalArgumentException {
		if (source == null)
			throw new IllegalArgumentException("source cannot be null");

		setSource(source, delimiters, delimiterMatchType, index, source.length
				- index);
	}

	public void setSource(byte[] source, byte[] delimiters,
			DelimiterMatchType delimiterMatchType, int index, int length)
			throws IllegalArgumentException {
		if (source == null || delimiters == null || delimiterMatchType == null)
			throw new IllegalArgumentException(
					"source, delimiters and delimiterMatchType cannot be null.");
		if (index < 0 || length < 0 || (index + length) > source.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index + length) ["
					+ (index + length) + "] must be <= source.length ["
					+ source.length + "]");
		if (delimiterMatchType == DelimiterMatchType.EXACT
				&& source.length < delimiters.length)
			throw new IllegalArgumentException(
					"DelimiterMatchType is EXACT, but source.length ["
							+ source.length
							+ "] was < delimiters.length ["
							+ delimiters.length
							+ "]. source must contain at least enough values to match against delimiters when using MATCH_ALL.");

		reset();

		this.index = index;
		this.length = length;
		this.endIndex = (index + length);
		this.source = source;
		this.delimiters = delimiters;
		this.delimiterMatchType = delimiterMatchType;
	}

	public IToken<byte[]> nextToken() throws IllegalStateException {
		if (source == null)
			throw new IllegalStateException(
					"source is null, setSource() must be called to prepare this tokenizer for work.");

		// Update the tsIndex and teIndex values marking the next token bounds
		nextTokenFast();

		IToken<byte[]> token = null;

		// Create the token only if valid bounds were found.
		if (!foundAllTokens)
			token = new ByteToken(tsIndex, (teIndex - tsIndex), source);

		return token;
	}

	public int[] nextTokenFast() throws IllegalStateException {
		if (source == null)
			throw new IllegalStateException(
					"source is null, setSource() must be called to prepare this tokenizer for work.");

		bounds[0] = ArrayUtils.INVALID_INDEX;
		bounds[1] = ArrayUtils.INVALID_INDEX;

		if (!foundAllTokens) {
			// If new, start at index. Otherwise start at previous end.
			tsIndex = (teIndex == ArrayUtils.INVALID_INDEX ? index : teIndex);

			/*
			 * Now we scan and find the start/end indices marking the bounds of
			 * the next token. Incase we are starting with tsIndex pointing at
			 * delims, we scan forward to the first non-delim byte, then hunt
			 * for the end index from there.
			 */
			switch (delimiterMatchType) {
			case ANY:
				tsIndex = ArrayUtils.indexAfterAnyNoCheck(delimiters, source,
						tsIndex, length - tsIndex + index);
				teIndex = ArrayUtils.indexOfAnyNoCheck(delimiters, source,
						tsIndex, length - tsIndex + index);
				break;

			case EXACT:
				tsIndex = ArrayUtils.indexAfterNoCheck(delimiters, source,
						tsIndex, length - tsIndex + index);
				teIndex = ArrayUtils.indexOfNoCheck(delimiters, source,
						tsIndex, length - tsIndex + index);
				break;
			}

			if (tsIndex >= endIndex || teIndex >= endIndex
					|| teIndex == ArrayUtils.INVALID_INDEX)
				foundAllTokens = true;
			else {
				bounds[0] = tsIndex;
				bounds[1] = (teIndex - tsIndex);
			}
		}

		return bounds;
	}
}