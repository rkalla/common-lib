package com.thebuzzmedia.common.parser;

public interface IToken<T> {
	public int getIndex();
	
	public int getLength();
	
	public T getSource();
}