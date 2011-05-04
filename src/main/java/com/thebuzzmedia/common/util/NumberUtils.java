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

public class NumberUtils {
	/**
	 * Used to parse a <code>byte</code> numeric value (-128 to 127) from a
	 * <code>byte[]</code> starting at the given index and using the given
	 * number of bytes. Parsing values from {@link Byte#MIN_VALUE} to
	 * {@link Byte#MAX_VALUE} are supported.
	 * <p/>
	 * Because this method cannot return an primitive value representing a
	 * failed parse attempt (e.g. -1) it will throw an exception if any of the
	 * input values are invalid or if a value cannot be parsed from the given
	 * array cleanly (i.e. non-ASCII bytes representing chars '0' through '9'
	 * (starting char '-' is allowed for negatives) are encountered).
	 * <p/>
	 * Care should be taken to either protect against bad values before hand or
	 * handle the exceptions by the caller.
	 * <p/>
	 * The implementation of this method is highly efficient, using no
	 * object-creation during parsing and only allocating a few primitives for
	 * state-tracking.
	 * 
	 * @param index
	 *            The index into the array where parsing will begin from.
	 * @param length
	 *            The number of bytes to parse to construct the final value.
	 * @param data
	 *            The array to parse the number from.
	 * 
	 * @return a <code>byte</code> numeric value as parsed from the given array.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>data</code> is <code>null</code> or empty, if
	 *             <code>length</code> &lt;= 0, if <code>index</code> is &lt; 0
	 *             or if <code>index + length</code> is &gt;
	 *             <code>data.length</code>.
	 * @throws NumberFormatException
	 *             if an non-numeric char with an ASCII code &lt; 48 (0) or &gt;
	 *             57 (9) is encountered (first char of '-' (for a negative) is
	 *             fine) also if the size of the value being parsed overflows
	 *             beyond the limits of {@link Byte#MAX_VALUE} or
	 *             {@link Byte#MIN_VALUE}.
	 */
	public static byte parseByte(int index, int length, byte[] data)
			throws IllegalArgumentException, NumberFormatException {
		return (byte) parseLong(index, length, data, Byte.MIN_VALUE,
				Byte.MAX_VALUE);
	}

	/**
	 * Used to parse a <code>short</code> value from a <code>byte[]</code>
	 * starting at the given index and using the given number of bytes. Parsing
	 * values from {@link Short#MIN_VALUE} to {@link Short#MAX_VALUE} are
	 * supported.
	 * <p/>
	 * Because this method cannot return an primitive value representing a
	 * failed parse attempt (e.g. -1) it will throw an exception if any of the
	 * input values are invalid or if a value cannot be parsed from the given
	 * array cleanly (i.e. non-ASCII bytes representing chars '0' through '9'
	 * (starting char '-' is allowed for negatives) are encountered).
	 * <p/>
	 * Care should be taken to either protect against bad values before hand or
	 * handle the exceptions by the caller.
	 * <p/>
	 * The implementation of this method is highly efficient, using no
	 * object-creation during parsing and only allocating a few primitives for
	 * state-tracking.
	 * 
	 * @param index
	 *            The index into the array where parsing will begin from.
	 * @param length
	 *            The number of bytes to parse to construct the final value.
	 * @param data
	 *            The array to parse the number from.
	 * 
	 * @return a <code>short</code> value as parsed from the given array.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>data</code> is <code>null</code> or empty, if
	 *             <code>length</code> &lt;= 0, if <code>index</code> is &lt; 0
	 *             or if <code>index + length</code> is &gt;
	 *             <code>data.length</code>.
	 * @throws NumberFormatException
	 *             if an non-numeric char with an ASCII code &lt; 48 (0) or &gt;
	 *             57 (9) is encountered (first char of '-' (for a negative) is
	 *             fine) also if the size of the value being parsed overflows
	 *             beyond the limits of {@link Short#MAX_VALUE} or
	 *             {@link Short#MIN_VALUE}.
	 */
	public static short parseShort(int index, int length, byte[] data)
			throws IllegalArgumentException, NumberFormatException {
		return (short) parseLong(index, length, data, Short.MIN_VALUE,
				Short.MAX_VALUE);
	}

	/**
	 * Used to parse an <code>integer</code> from a <code>byte[]</code> starting
	 * at the given index and using the given number of bytes. Parsing values
	 * from {@link Integer#MIN_VALUE} to {@link Integer#MAX_VALUE} are
	 * supported.
	 * <p/>
	 * Because this method cannot return an primitive value representing a
	 * failed parse attempt (e.g. -1) it will throw an exception if any of the
	 * input values are invalid or if a value cannot be parsed from the given
	 * array cleanly (i.e. non-ASCII bytes representing chars '0' through '9'
	 * (starting char '-' is allowed for negatives) are encountered).
	 * <p/>
	 * Care should be taken to either protect against bad values before hand or
	 * handle the exceptions by the caller.
	 * <p/>
	 * The implementation of this method is highly efficient, using no
	 * object-creation during parsing and only allocating a few primitives for
	 * state-tracking.
	 * 
	 * @param index
	 *            The index into the array where parsing will begin from.
	 * @param length
	 *            The number of bytes to parse to construct the final value.
	 * @param data
	 *            The array to parse the number from.
	 * 
	 * @return an <code>integer</code> value as parsed from the given array.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>data</code> is <code>null</code> or empty, if
	 *             <code>length</code> &lt;= 0, if <code>index</code> is &lt; 0
	 *             or if <code>index + length</code> is &gt;
	 *             <code>data.length</code>.
	 * @throws NumberFormatException
	 *             if an non-numeric char with an ASCII code &lt; 48 (0) or &gt;
	 *             57 (9) is encountered (first char of '-' (for a negative) is
	 *             fine) also if the size of the value being parsed overflows
	 *             beyond the limits of {@link Integer#MAX_VALUE} or
	 *             {@link Integer#MIN_VALUE}.
	 */
	public static int parseInt(int index, int length, byte[] data)
			throws IllegalArgumentException, NumberFormatException {
		return (int) parseLong(index, length, data, Integer.MIN_VALUE,
				Integer.MAX_VALUE);
	}

	/**
	 * Used to parse a <code>long</code> from a <code>byte[]</code> starting at
	 * the given index and using the given number of bytes. Parsing values from
	 * {@link Long#MIN_VALUE} to {@link Long#MAX_VALUE} are supported.
	 * <p/>
	 * Because this method cannot return an primitive value representing a
	 * failed parse attempt (e.g. -1) it will throw an exception if any of the
	 * input values are invalid or if a value cannot be parsed from the given
	 * array cleanly (i.e. non-ASCII bytes representing chars '0' through '9'
	 * (starting char '-' is allowed for negatives) are encountered).
	 * <p/>
	 * Care should be taken to either protect against bad values before hand or
	 * handle the exceptions by the caller.
	 * <p/>
	 * The implementation of this method is highly efficient, using no
	 * object-creation during parsing and only allocating a few primitives for
	 * state-tracking.
	 * 
	 * @param index
	 *            The index into the array where parsing will begin from.
	 * @param length
	 *            The number of bytes to parse to construct the final value.
	 * @param data
	 *            The array to parse the number from.
	 * 
	 * @return a <code>long</code> value as parsed from the given array.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>data</code> is <code>null</code> or empty, if
	 *             <code>length</code> &lt;= 0, if <code>index</code> is &lt; 0
	 *             or if <code>index + length</code> is &gt;
	 *             <code>data.length</code>.
	 * @throws NumberFormatException
	 *             if an non-numeric char with an ASCII code &lt; 48 (0) or &gt;
	 *             57 (9) is encountered (first char of '-' (for a negative) is
	 *             fine) also if the size of the value being parsed overflows
	 *             beyond the limits of {@link Long#MAX_VALUE} or
	 *             {@link Long#MIN_VALUE}.
	 */
	public static long parseLong(int index, int length, byte[] data)
			throws IllegalArgumentException, NumberFormatException {
		return parseLong(index, length, data, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	protected static long parseLong(int index, int length, byte[] data,
			long minValue, long maxValue) throws IllegalArgumentException,
			NumberFormatException {
		if (data == null || data.length == 0)
			throw new IllegalArgumentException("data cannot be null or empty");
		if (index < 0 || length <= 0 || (index + length > data.length))
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0 and length [" + length
					+ "] must be > 0 and (index + length) [" + (index + length)
					+ "] must be <= data.length [" + data.length + "]");

		long result = 0;

		// Check the first byte to see if this is a negative number: '-' is 45
		boolean neg = (data[index] == 45 ? true : false);

		/*
		 * Begin iterating on the bytes, parsing the number. If we are dealing
		 * with a negative number, start on the index + 1 element because the
		 * element at index is '-'.
		 */
		for (int i = (neg ? 1 : 0); i < length; i++) {
			/*
			 * Get the byte representing the digit. Convert it quickly to an int
			 * by subtracting the ASCII value of '0' from it (48); yielding its
			 * true int value: 0 through 9
			 */
			long digit = (data[index + i] - 48);

			/*
			 * If the digit parsed is less than 0 or greater than 9, then it
			 * wasn't an ASCII character byte representing a number and we have
			 * to throw an exception notifying the caller that this run of bytes
			 * does not represent a number.
			 */
			if (digit < 0 || digit > 9)
				throw new NumberFormatException(
						"A non-numeric value (ASCII code " + (digit + 48)
								+ ") was encountered at index " + (index + i)
								+ " while trying to parse an integer from '"
								+ new String(data, index, length) + "'.");

			/*
			 * Adjust the value of the parsed number by magnitudes of 10 to
			 * ensure its value is adjusted for the right place it was in (e.g.
			 * one's place, ten's place, hundred's, etc.)
			 */
			for (int j = i, end = length - 1; j < end; j++)
				digit *= 10;

			/*
			 * Add the parsed value to the running total. We store all values
			 * during calculation as negatives because the MIN_VALUE bound of
			 * all primitive types holds 1 more value than the positive bounds
			 * (e.g. -128 to 127 for byte).
			 * 
			 * We just flip the negative when we return the value if necessary.
			 * 
			 * TIP from: http://nadeausoftware.com/node/97
			 */
			result -= digit;

			/*
			 * Now we check to make sure the value hasn't overflowed the lower
			 * bounds of the primitive type we were parsing for.
			 * 
			 * Since we only parse negative values (and flip the negative as a
			 * last step if necessary) we can check against our minValue for any
			 * primitive type smaller than a long, and for a long, check if the
			 * type has flipped to a positive value to indicate an overflow.
			 */
			if (result < minValue || result > 0)
				throw new NumberFormatException(
						"Value has overflowed. The number represented in the given array from index "
								+ index
								+ " to "
								+ (index + length)
								+ " exclusive ("
								+ new String(data, index, length)
								+ ") is larger than the limits of the target primitive type with value bounds of "
								+ minValue + " to " + maxValue + ".");
		}

		// Flip the sign if it was positive or leave it negative.
		return (neg ? result : -result);
	}
}