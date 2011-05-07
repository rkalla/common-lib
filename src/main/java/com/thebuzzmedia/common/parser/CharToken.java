package com.thebuzzmedia.common.parser;

public class CharToken extends AbstractToken<char[]> {
	public CharToken(int index, int length, char[] source)
			throws IllegalArgumentException {
		super(index, length, source);
	}

	@Override
	public String toString() {
		return new String(source, index, length);
	}
}