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
package com.thebuzzmedia.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberUtilsTest {
	public static final byte[] NEG = { 45, 49, 55 }; // -17
	public static final byte[] POS = { 54, 52 }; // 64
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
		assertEquals(-17, NumberUtils.parseByte(NEG, 0, 3));
		assertEquals(64, NumberUtils.parseByte(POS, 0, 2));
		assertEquals(0, NumberUtils.parseByte(ZERO, 0, 1));
		assertEquals(-73, NumberUtils.parseByte(NEG_Z_POS, 0, 3));
		assertEquals(0, NumberUtils.parseByte(NEG_Z_POS, 3, 1));
		assertEquals(73, NumberUtils.parseByte(NEG_Z_POS, 4, 2));

		int i = BYTE.length / 2 + 1;

		assertEquals(Byte.MIN_VALUE, NumberUtils.parseByte(BYTE, 0, i));
		assertEquals(Byte.MAX_VALUE, NumberUtils.parseByte(BYTE, i, i - 1));
	}

	@Test
	public void testParseShort() {
		assertEquals(-17, NumberUtils.parseShort(NEG, 0, 3));
		assertEquals(64, NumberUtils.parseShort(POS, 0, 2));
		assertEquals(0, NumberUtils.parseShort(ZERO, 0, 1));
		assertEquals(-73, NumberUtils.parseShort(NEG_Z_POS, 0, 3));
		assertEquals(0, NumberUtils.parseShort(NEG_Z_POS, 3, 1));
		assertEquals(73, NumberUtils.parseShort(NEG_Z_POS, 4, 2));

		int i = SHORT.length / 2 + 1;

		assertEquals(Short.MIN_VALUE, NumberUtils.parseShort(SHORT, 0, i));
		assertEquals(Short.MAX_VALUE, NumberUtils.parseShort(SHORT, i, i - 1));
	}

	@Test
	public void testParseInt() {
		assertEquals(-17, NumberUtils.parseInt(NEG, 0, 3));
		assertEquals(64, NumberUtils.parseInt(POS, 0, 2));
		assertEquals(0, NumberUtils.parseInt(ZERO, 0, 1));
		assertEquals(-73, NumberUtils.parseInt(NEG_Z_POS, 0, 3));
		assertEquals(0, NumberUtils.parseInt(NEG_Z_POS, 3, 1));
		assertEquals(73, NumberUtils.parseInt(NEG_Z_POS, 4, 2));

		int i = INT.length / 2 + 1;

		assertEquals(Integer.MIN_VALUE, NumberUtils.parseInt(INT, 0, i));
		assertEquals(Integer.MAX_VALUE, NumberUtils.parseInt(INT, i, i - 1));
	}

	@Test
	public void testParseLong() {
		assertEquals(-17, NumberUtils.parseLong(NEG, 0, 3));
		assertEquals(64, NumberUtils.parseLong(POS, 0, 2));
		assertEquals(0, NumberUtils.parseLong(ZERO, 0, 1));
		assertEquals(-73, NumberUtils.parseLong(NEG_Z_POS, 0, 3));
		assertEquals(0, NumberUtils.parseLong(NEG_Z_POS, 3, 1));
		assertEquals(73, NumberUtils.parseLong(NEG_Z_POS, 4, 2));

		int i = LONG.length / 2 + 1;

		assertEquals(Long.MIN_VALUE, NumberUtils.parseLong(LONG, 0, i));
		assertEquals(Long.MAX_VALUE, NumberUtils.parseLong(LONG, i, i - 1));
	}
}