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

// TODO: Add equals methods that accept index and offsets to do comparisons
public class ArrayUtils {
	public static final int INVALID_INDEX = -1;

	/*
	 * ========================================================================
	 * byte[] array, byte value
	 * ========================================================================
	 */
	public static int indexOf(byte[] array, byte value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(0, array.length, array, value);
	}

	public static int indexOf(int index, byte[] array, byte value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(index, array.length - index, array, value);
	}

	public static int indexOf(int index, int length, byte[] array, byte value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		for (int end = (index + length); index < end; index++) {
			if (array[index] == value)
				return index;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * byte[] array, byte[] values
	 * ========================================================================
	 */
	public static int indexOf(byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(0, array.length, array, values);
	}

	public static int indexOf(int index, byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(index, array.length - index, array, values);
	}

	public static int indexOf(int index, int length, byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (length < values.length)
			throw new IllegalArgumentException("length [" + length
					+ "] must be >= values.length [" + values.length + "]");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		// Loop until we cannot match values anymore
		for (int end = (index + length - values.length); index <= end; index++) {
			// Increment j as long as we can match values in-order
			for (j = 0; j < values.length && values[j] == array[index + j]; j++)
				;

			// If j is values.length, we matched all.
			if (j == values.length)
				return index;
			else
				index += j;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	public static int indexOfAny(byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(0, array.length, array, values);
	}

	public static int indexOfAny(int index, byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(index, array.length - index, array, values);
	}

	public static int indexOfAny(int index, int length, byte[] array,
			byte[] values) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		for (int end = (index + length); index < end; index++) {
			byte b = array[index];

			for (j = 0; j < values.length; j++) {
				if (b == values[j])
					return index;
			}
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte value
	 * ========================================================================
	 */
	public static int lastIndexOf(byte[] array, byte value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(0, array.length, array, value);
	}

	public static int lastIndexOf(int index, byte[] array, byte value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(index, array.length - index, array, value);
	}

	public static int lastIndexOf(int index, int length, byte[] array,
			byte value) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		for (int i = (index + length - 1); i >= index; i--) {
			if (array[i] == value)
				return i;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte[] values
	 * ========================================================================
	 */
	public static int lastIndexOf(byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(0, array.length, array, values);
	}

	public static int lastIndexOf(int index, byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(index, array.length - index, array, values);
	}

	public static int lastIndexOf(int index, int length, byte[] array,
			byte[] values) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (length < values.length)
			throw new IllegalArgumentException("length [" + length
					+ "] must be >= values.length [" + values.length + "]");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		// Loop until it isn't possible for us to match values
		for (int i = (index + length - 1), endIndex = (index + values.length - 1); i >= endIndex; i--) {
			// Increment j for as many matches as we find
			for (j = 0; j < values.length
					&& array[i - j] == values[values.length - 1 - j]; j++)
				;

			// If we incremented j all the way, we matched all of values
			if (j == values.length)
				return i - j + 1;
			else
				i -= j;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	public static int lastIndexOfAny(byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(0, array.length, array, values);
	}

	public static int lastIndexOfAny(int index, byte[] array, byte[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(index, array.length - index, array, values);
	}

	public static int lastIndexOfAny(int index, int length, byte[] array,
			byte[] values) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		for (int i = (index + length - 1); i >= index; i--) {
			byte b = array[i];

			for (j = 0; j < values.length; j++) {
				if (b == values[j])
					return i;
			}
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * char[] array, char value
	 * ========================================================================
	 */
	public static int indexOf(char[] array, char value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(0, array.length, array, value);
	}

	public static int indexOf(int index, char[] array, char value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(index, array.length - index, array, value);
	}

	public static int indexOf(int index, int length, char[] array, char value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		for (int end = (index + length); index < end; index++) {
			if (array[index] == value)
				return index;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * char[] array, char[] values
	 * ========================================================================
	 */
	public static int indexOf(char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(0, array.length, array, values);
	}

	public static int indexOf(int index, char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(index, array.length - index, array, values);
	}

	public static int indexOf(int index, int length, char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (length < values.length)
			throw new IllegalArgumentException("length [" + length
					+ "] must be >= values.length [" + values.length + "]");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		// Loop until we cannot match values anymore
		for (int end = (index + length - values.length); index <= end; index++) {
			// Increment j as long as we can match values in-order
			for (j = 0; j < values.length && values[j] == array[index + j]; j++)
				;

			// If j is values.length, we matched all.
			if (j == values.length)
				return index;
			else
				index += j;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * char[] array, ANY char[] values
	 * ========================================================================
	 */
	public static int indexOfAny(char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(0, array.length, array, values);
	}

	public static int indexOfAny(int index, char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(index, array.length - index, array, values);
	}

	public static int indexOfAny(int index, int length, char[] array,
			char[] values) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		for (int end = (index + length); index < end; index++) {
			char b = array[index];

			for (j = 0; j < values.length; j++) {
				if (b == values[j])
					return index;
			}
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, char value
	 * ========================================================================
	 */
	public static int lastIndexOf(char[] array, char value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(0, array.length, array, value);
	}

	public static int lastIndexOf(int index, char[] array, char value)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(index, array.length - index, array, value);
	}

	public static int lastIndexOf(int index, int length, char[] array,
			char value) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		for (int i = (index + length - 1); i >= index; i--) {
			if (array[i] == value)
				return i;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, char[] values
	 * ========================================================================
	 */
	public static int lastIndexOf(char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(0, array.length, array, values);
	}

	public static int lastIndexOf(int index, char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(index, array.length - index, array, values);
	}

	public static int lastIndexOf(int index, int length, char[] array,
			char[] values) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (length < values.length)
			throw new IllegalArgumentException("length [" + length
					+ "] must be >= values.length [" + values.length + "]");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		// Loop until it isn't possible for us to match values
		for (int i = (index + length - 1), endIndex = (index + values.length - 1); i >= endIndex; i--) {
			// Increment j for as many matches as we find
			for (j = 0; j < values.length
					&& array[i - j] == values[values.length - 1 - j]; j++)
				;

			// If we incremented j all the way, we matched all of values
			if (j == values.length)
				return i - j + 1;
			else
				i -= j;
		}

		return INVALID_INDEX;
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, ANY char[] values
	 * ========================================================================
	 */
	public static int lastIndexOfAny(char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(0, array.length, array, values);
	}

	public static int lastIndexOfAny(int index, char[] array, char[] values)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(index, array.length - index, array, values);
	}

	public static int lastIndexOfAny(int index, int length, char[] array,
			char[] values) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (values == null)
			throw new IllegalArgumentException("values cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		// pre-define once
		int j;

		for (int i = (index + length - 1); i >= index; i--) {
			char b = array[i];

			for (j = 0; j < values.length; j++) {
				if (b == values[j])
					return i;
			}
		}

		return INVALID_INDEX;
	}
}