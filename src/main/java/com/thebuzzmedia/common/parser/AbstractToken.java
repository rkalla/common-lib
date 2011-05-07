package com.thebuzzmedia.common.parser;

public abstract class AbstractToken<T> implements IToken<T> {
	protected int index;
	protected int length;
	protected T source;

	public AbstractToken(int index, int length, T source)
			throws IllegalArgumentException {
		if (source == null)
			throw new IllegalArgumentException("source cannot be null");
		if (index < 0 || length < 0)
			throw new IllegalArgumentException("index [" + index
					+ "] and length [" + length + "] must be >= 0");

		this.index = index;
		this.length = length;
		this.source = source;
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
}