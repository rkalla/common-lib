package com.thebuzzmedia.common.parser;

public interface ITokenizer<T> {
	public enum DelimiterMatchType {
		ANY, EXACT;
	}

	public void reset();

	public int getIndex();

	public int getLength();

	public T getSource();

	public T getDelimiters();

	public DelimiterMatchType getDelimiterMatchType();

	public void setSource(T source, T delimiters)
			throws IllegalArgumentException;

	public void setSource(T source, T delimiters,
			DelimiterMatchType delimiterMatchType)
			throws IllegalArgumentException;

	public void setSource(T source, T delimiters,
			DelimiterMatchType delimiterMatchType, int index)
			throws IllegalArgumentException;

	public void setSource(T source, T delimiters,
			DelimiterMatchType delimiterMatchType, int index, int length)
			throws IllegalArgumentException;

	public IToken<T> nextToken() throws IllegalStateException;

	public int[] nextTokenFast() throws IllegalStateException;
}