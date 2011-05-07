package com.thebuzzmedia.common.parser;

import com.thebuzzmedia.common.util.ArrayUtils;

public abstract class AbstractTokenizer<T> implements ITokenizer<T> {
	protected boolean foundAllTokens;

	protected int index;
	protected int length;
	protected int endIndex;

	protected int tsIndex;
	protected int teIndex;

	protected T source;
	protected T delimiters;
	protected DelimiterMatchType delimiterMatchType;

	protected int[] bounds;

	public AbstractTokenizer() {
		bounds = new int[2];
	}

	public void reset() {
		foundAllTokens = false;

		index = ArrayUtils.INVALID_INDEX;
		length = 0;
		endIndex = ArrayUtils.INVALID_INDEX;

		tsIndex = ArrayUtils.INVALID_INDEX;
		teIndex = ArrayUtils.INVALID_INDEX;

		source = null;
		delimiters = null;
		delimiterMatchType = null;

		bounds[0] = ArrayUtils.INVALID_INDEX;
		bounds[1] = ArrayUtils.INVALID_INDEX;
	}

	public int getIndex() {
		return index;
	}

	public int getLength() {
		return length;
	}

	public T getSource() {
		return source;
	}

	public T getDelimiters() {
		return delimiters;
	}

	public DelimiterMatchType getDelimiterMatchType() {
		return delimiterMatchType;
	}
}