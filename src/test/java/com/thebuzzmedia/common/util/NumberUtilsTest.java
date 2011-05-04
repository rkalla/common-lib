package com.thebuzzmedia.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberUtilsTest {
	public static final byte[] NEG = { 45, 49, 55 }; // -17
	public static final byte[] POS = { 54,52 }; // 64
	public static final byte[] ZERO = { 48 }; // 0
	public static final byte[] NEG_Z_POS = { 45, 55, 51, 48, 55, 51 }; // -73073

	public static final byte[] BYTE = { 45, 49, 50, 56, 49, 50, 55 };
	public static final byte[] SHORT = { 45, 51, 50, 55, 54, 56, 51, 50, 55,
			54, 55 };
	public static final byte[] INT = { 45, 50, 49, 52, 55, 52, 56, 51, 54, 52,
			56, 50, 49, 52, 55, 52, 56, 51, 54, 52, 55 };
	public static final byte[] LONG = { 45, 57, 50, 50, 51, 51, 55, 50, 48, 51,
			54, 56, 53, 52, 55, 55, 53, 56, 48, 56, 57, 50, 50, 51, 51, 55, 50,
			48, 51, 54, 56, 53, 52, 55, 55, 53, 56, 48, 55 };

	@Test
	public void testParseByte() {
		assertEquals(-17, NumberUtils.parseByte(0, 3, NEG));
		assertEquals(64, NumberUtils.parseByte(0, 2, POS));
		assertEquals(0, NumberUtils.parseByte(0, 1, ZERO));
		assertEquals(-73, NumberUtils.parseByte(0, 3, NEG_Z_POS));
		assertEquals(0, NumberUtils.parseByte(3, 1, NEG_Z_POS));
		assertEquals(73, NumberUtils.parseByte(4, 2, NEG_Z_POS));
		
		int i = BYTE.length / 2 + 1;
		
		assertEquals(Byte.MIN_VALUE, NumberUtils.parseByte(0, i, BYTE));
		assertEquals(Byte.MAX_VALUE, NumberUtils.parseByte(i, i - 1, BYTE));
	}
	
	@Test
	public void testParseShort() {
		assertEquals(-17, NumberUtils.parseShort(0, 3, NEG));
		assertEquals(64, NumberUtils.parseShort(0, 2, POS));
		assertEquals(0, NumberUtils.parseShort(0, 1, ZERO));
		assertEquals(-73, NumberUtils.parseShort(0, 3, NEG_Z_POS));
		assertEquals(0, NumberUtils.parseShort(3, 1, NEG_Z_POS));
		assertEquals(73, NumberUtils.parseShort(4, 2, NEG_Z_POS));
		
		int i = SHORT.length / 2 + 1;
		
		assertEquals(Short.MIN_VALUE, NumberUtils.parseShort(0, i, SHORT));
		assertEquals(Short.MAX_VALUE, NumberUtils.parseShort(i, i - 1, SHORT));
	}

	@Test
	public void testParseInt() {
		assertEquals(-17, NumberUtils.parseInt(0, 3, NEG));
		assertEquals(64, NumberUtils.parseInt(0, 2, POS));
		assertEquals(0, NumberUtils.parseInt(0, 1, ZERO));
		assertEquals(-73, NumberUtils.parseInt(0, 3, NEG_Z_POS));
		assertEquals(0, NumberUtils.parseInt(3, 1, NEG_Z_POS));
		assertEquals(73, NumberUtils.parseInt(4, 2, NEG_Z_POS));
		
		int i = INT.length / 2 + 1;
		
		assertEquals(Integer.MIN_VALUE, NumberUtils.parseInt(0, i, INT));
		assertEquals(Integer.MAX_VALUE, NumberUtils.parseInt(i, i - 1, INT));
	}

	@Test
	public void testParseLong() {
		assertEquals(-17, NumberUtils.parseLong(0, 3, NEG));
		assertEquals(64, NumberUtils.parseLong(0, 2, POS));
		assertEquals(0, NumberUtils.parseLong(0, 1, ZERO));
		assertEquals(-73, NumberUtils.parseLong(0, 3, NEG_Z_POS));
		assertEquals(0, NumberUtils.parseLong(3, 1, NEG_Z_POS));
		assertEquals(73, NumberUtils.parseLong(4, 2, NEG_Z_POS));
		
		int i = LONG.length / 2 + 1;
		
		assertEquals(Long.MIN_VALUE, NumberUtils.parseLong(0, i, LONG));
		assertEquals(Long.MAX_VALUE, NumberUtils.parseLong(i, i - 1, LONG));
	}
}