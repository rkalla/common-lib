package com.thebuzzmedia.common.parser;

public class ByteToken extends AbstractToken<byte[]> {
	public ByteToken(int index, int length, byte[] source)
			throws IllegalArgumentException {
		super(index, length, source);
	}

	@Override
	public String toString() {
		return new String(source, index, length);
	}
}