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

	public byte[] toArray() {
		byte[] copy = new byte[length];
		System.arraycopy(source, index, copy, 0, length);
		return copy;
	}
}