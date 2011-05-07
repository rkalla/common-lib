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

public class RandomUtilsTest {
	@Test
	public void testRandomIntI() {
		int min = 0;
		int max = 0;

		assertEquals(0, RandomUtils.randomInt(max));

		max = 1;
		int v = RandomUtils.randomInt(max);

		assertTrue(v == 0 || v == 1);

		max = Integer.MAX_VALUE / 2;

		for (int i = 0; i < 1000000; i++) {
			int num = RandomUtils.randomInt(max);

			assertTrue(num >= min);
			assertTrue(num <= max);
		}
	}

	@Test
	public void testRandomIntII() {
		int min = 0;
		int max = 0;

		assertEquals(0, RandomUtils.randomInt(min, max));

		max = 1;
		int v = RandomUtils.randomInt(min, max);

		assertTrue(v == 0 || v == 1);

		min = 1;
		assertEquals(1, RandomUtils.randomInt(min, max));

		min = 50;
		max = 100;
		v = RandomUtils.randomInt(min, max);

		assertTrue(v >= min && v <= max);

		min = Integer.MAX_VALUE / 2;
		max = Integer.MAX_VALUE;

		for (int i = 0; i < 1000000; i++) {
			int num = RandomUtils.randomInt(min, max);

			assertTrue(num >= min);
			assertTrue(num <= max);
		}
	}

	@Test
	public void testRandomLongL() {
		long min = 0;
		long max = 0;

		assertEquals(0, RandomUtils.randomLong(max));

		max = 1;
		long v = RandomUtils.randomLong(max);

		assertTrue(v == 0 || v == 1);

		max = Long.MAX_VALUE / 2;

		for (int i = 0; i < 1000000; i++) {
			long num = RandomUtils.randomLong(max);

			assertTrue(num >= min);
			assertTrue(num <= max);
		}
	}

	@Test
	public void testRandomLongLL() {
		long min = 0;
		long max = 0;

		assertEquals(0, RandomUtils.randomLong(min, max));

		max = 1;
		long v = RandomUtils.randomLong(min, max);

		assertTrue(v == 0 || v == 1);

		min = 1;
		assertEquals(1, RandomUtils.randomLong(min, max));

		min = 50;
		max = 100;
		v = RandomUtils.randomLong(min, max);

		assertTrue(v >= min && v <= max);

		min = Long.MAX_VALUE / 2;
		max = Long.MAX_VALUE;

		for (int i = 0; i < 1000000; i++) {
			long num = RandomUtils.randomLong(min, max);

			assertTrue(num >= min);
			assertTrue(num <= max);
		}
	}

	@Test
	public void testRandomCharsCaI() {
		assertEquals(RandomUtils.EMPTY_CHARS,
				RandomUtils.randomChars(RandomUtils.FULL_ASCII_ALPHABET, 0));

		boolean b = false;
		char[] c = RandomUtils.randomChars(RandomUtils.NUMBER_ALPHABET, 1);

		for (int i = 0; i < RandomUtils.NUMBER_ALPHABET.length && !b; i++) {
			if (RandomUtils.NUMBER_ALPHABET[i] == c[0])
				b = true;
		}

		assertTrue(b);

		/*
		 * Generate 512-char strings using every alphabet, and verify every chat
		 * in every alphabet for every array generated.
		 */
		char[][] alphabets = { RandomUtils.NUMBER_ALPHABET,
				RandomUtils.SYMBOL_ALPHABET,
				RandomUtils.NUMERIC_SYMBOLIC_ALPHABET,
				RandomUtils.UPPER_CASE_ALPHABET,
				RandomUtils.LOWER_CASE_ALPHABET,
				RandomUtils.ALPHA_NUMERIC_ALPHABET,
				RandomUtils.FULL_ASCII_ALPHABET };

		for (int i = 0; i < alphabets.length; i++) {
			char[] alphabet = alphabets[i];
			c = RandomUtils.randomChars(alphabet, 512);

			// Verify every single char in result was from alphabet
			for (int j = 0; j < c.length; j++) {
				assertTrue(ArrayUtils.indexOf(c[j], alphabet) > -1);
			}
		}
	}
}