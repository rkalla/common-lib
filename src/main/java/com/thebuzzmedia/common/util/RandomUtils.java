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

import java.util.Random;

public class RandomUtils {
	public static final char[] NUMBER_ALPHABET = { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9' };
	public static final char[] SYMBOL_ALPHABET = { '!', '\"', '#', '$', '%',
			'&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<',
			'=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}',
			'~' };
	public static final char[] LOWER_CASE_ALPHABET = { 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	public static final char[] UPPER_CASE_ALPHABET = { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static final char[] UPPER_AND_LOWER_CASE_ALPHABET = { 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	public static final char[] ALPHA_NUMERIC_ALPHABET = { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9' };
	public static final char[] NUMERIC_SYMBOLIC_ALPHABET = { '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', '!', '\"', '#', '$', '%', '&',
			'\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=',
			'>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~' };

	public static final char[] FULL_ASCII_ALPHABET = { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '!', '\"', '#', '$', '%', '&', '\'', '(',
			')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?',
			'@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~' };

	public static final char[] EMPTY_CHARS = new char[0];

	private static final Random RANDOM = new Random(System.currentTimeMillis());

	public static int randomInt(int max) throws IllegalArgumentException {
		return randomInt(0, max);
	}

	public static int randomInt(int min, int max)
			throws IllegalArgumentException {
		if (min < 0 || max < 0 || min > max)
			throw new IllegalArgumentException("min [" + min
					+ "] must be >= 0, max [" + max
					+ "] must be >= 0 and >= min.");

		double d = RANDOM.nextDouble();
		return (int) (d * (double) (max - min) + min);
	}

	public static long randomLong(long max) throws IllegalArgumentException {
		return randomLong(0, max);
	}

	public static long randomLong(long min, long max)
			throws IllegalArgumentException {
		if (min < 0 || max < 0 || min > max)
			throw new IllegalArgumentException("min [" + min
					+ "] must be >= 0, max [" + max
					+ "] must be >= 0 and >= min.");

		double d = RANDOM.nextDouble();
		return (long) (d * (double) (max - min) + min);
	}

	public static char[] randomChars(char[] alphabet, int length)
			throws IllegalArgumentException {
		if (alphabet == null || alphabet.length == 0)
			throw new IllegalArgumentException(
					"alphabet cannot be null or empty, it must contain a valid list of characters to pull values from. Many complete alpha, numeric and symbolic alphabets are pre-defined by this class for ease of use.");
		if (length < 0)
			throw new IllegalArgumentException("length must be >= 0");
		if (length == 0)
			return EMPTY_CHARS;

		int max = alphabet.length - 1;
		char[] result = new char[length];

		for (int i = 0; i < result.length; i++)
			result[i] = alphabet[randomInt(max)];

		return result;
	}
}