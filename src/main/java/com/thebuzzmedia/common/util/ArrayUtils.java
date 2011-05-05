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
	 * ########################################################################
	 * 
	 * 
	 * 
	 * BYTE-bsaed methods
	 * 
	 * 
	 * 
	 * ########################################################################
	 * ========================================================================
	 */

	public static boolean equals(byte[] source, byte[] values) {
		// Short-circuit in simple cases.
		if ((source == values) || (source == null && values == null))
			return true;
		else if ((source == null && values != null)
				|| (source != null && values == null))
			return false;
		else if (source.length != values.length)
			return false;

		return equals(0, source, 0, values, values.length);
	}

	public static boolean equals(int sourceIndex, byte[] source,
			int valuesIndex, byte[] values, int length)
			throws IllegalArgumentException {
		// Short-circuit in simple cases.
		if (source == values)
			return true;
		else if ((source == null && values != null)
				|| (source != null && values == null))
			return false;

		/*
		 * Check all the arguments. Seems lengthy, but it's fast and allows the
		 * code below it to be simpler. We have to do these sanity checks at
		 * SOME regardless.
		 */
		if (sourceIndex < 0 || valuesIndex < 0 || length < 0
				|| (sourceIndex + length) > source.length
				|| (valuesIndex + length) > values.length)
			throw new IllegalArgumentException("sourceIndex [" + sourceIndex
					+ "] and valuesIndex [" + valuesIndex
					+ "] must be >= 0. length [" + length
					+ "] must be >= 0. (sourceIndex + length) ["
					+ (sourceIndex + length) + "] must be <= source.length ["
					+ source.length + "]. (valuesIndex + length) ["
					+ (valuesIndex + length) + "]  must be <= values.length ["
					+ values.length + "].");

		for (int i = 0; i < length; i++) {
			if (source[sourceIndex + i] != values[valuesIndex + i])
				return false;
		}

		return true;
	}

	public static byte[] ensureCapacity(int capacity, byte[] array) {
		return ensureCapacity(capacity, 1, array);
	}

	public static byte[] ensureCapacity(int capacity, float growthFactor,
			byte[] array) {
		if (capacity < array.length)
			return array;

		// Calculate the new capacity based on the growth factor.
		int newCapacity = (int) ((float) array.length * growthFactor);

		// Double new capacity it at least big enough for requested amount.
		if (newCapacity < capacity)
			newCapacity = capacity;

		// Create new array and return it.
		byte[] newArray = new byte[newCapacity];
		System.arraycopy(array, 0, newArray, 0, array.length);
		return newArray;
	}

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
	 * ########################################################################
	 * 
	 * 
	 * 
	 * CHAR-bsaed methods
	 * 
	 * 
	 * 
	 * ########################################################################
	 * ========================================================================
	 */
	public static boolean equals(char[] source, char[] values) {
		// Short-circuit in simple cases.
		if ((source == values) || (source == null && values == null))
			return true;
		else if ((source == null && values != null)
				|| (source != null && values == null))
			return false;
		else if (source.length != values.length)
			return false;

		return equals(0, source, 0, values, values.length);
	}

	public static boolean equals(int sourceIndex, char[] source,
			int valuesIndex, char[] values, int length)
			throws IllegalArgumentException {
		// Short-circuit in simple cases.
		if (source == values)
			return true;
		else if ((source == null && values != null)
				|| (source != null && values == null))
			return false;

		/*
		 * Check all the arguments. Seems lengthy, but it's fast and allows the
		 * code below it to be simpler. We have to do these sanity checks at
		 * SOME regardless.
		 */
		if (sourceIndex < 0 || valuesIndex < 0 || length < 0
				|| (sourceIndex + length) > source.length
				|| (valuesIndex + length) > values.length)
			throw new IllegalArgumentException("sourceIndex [" + sourceIndex
					+ "] and valuesIndex [" + valuesIndex
					+ "] must be >= 0. length [" + length
					+ "] must be >= 0. (sourceIndex + length) ["
					+ (sourceIndex + length) + "] must be <= source.length ["
					+ source.length + "]. (valuesIndex + length) ["
					+ (valuesIndex + length) + "]  must be <= values.length ["
					+ values.length + "].");

		for (int i = 0; i < length; i++) {
			if (source[sourceIndex + i] != values[valuesIndex + i])
				return false;
		}

		return true;
	}

	public static char[] ensureCapacity(int capacity, char[] array) {
		return ensureCapacity(capacity, 1, array);
	}

	public static char[] ensureCapacity(int capacity, float growthFactor,
			char[] array) {
		if (capacity < array.length)
			return array;

		// Calculate the new capacity based on the growth factor.
		int newCapacity = (int) ((float) array.length * growthFactor);

		// Double new capacity it at least big enough for requested amount.
		if (newCapacity < capacity)
			newCapacity = capacity;

		// Create new array and return it.
		char[] newArray = new char[newCapacity];
		System.arraycopy(array, 0, newArray, 0, array.length);
		return newArray;
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