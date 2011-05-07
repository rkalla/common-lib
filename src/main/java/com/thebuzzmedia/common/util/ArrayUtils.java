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

// TODO: add indexNOTof methods.
// TODO: Add method that copies array A into array B (resizing if necessary) and returns result.
// basically makes appending/inserting into an array easier.
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
	public static byte[] append(byte[] values, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return insert(values, array, array.length);
	}

	public static byte[] insert(byte[] values, byte[] array, int index)
			throws IllegalArgumentException {
		if (values == null || array == null)
			throw new IllegalArgumentException(
					"Neither values or array can be null.");
		if (index < 0 || index > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0 and <= array.length [" + array.length
					+ "]");

		// Remember the array's original length
		int originalLength = array.length;

		// Ensure the final size of the array
		array = ensureCapacity(array, array.length + values.length);

		// Move contents down to make room if this is an insert.
		if (index < array.length)
			System.arraycopy(array, index, array, index + values.length,
					originalLength - index);

		// Copy the values into the resulting array
		System.arraycopy(values, 0, array, index, values.length);

		return array;
	}

	public static byte[] ensureCapacity(byte[] array, int capacity) {
		return ensureCapacity(array, capacity, 1);
	}

	public static byte[] ensureCapacity(byte[] array, int capacity,
			float growthFactor) {
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

	public static boolean equals(byte[] values, byte[] array) {
		// Short-circuit in simple cases.
		if ((array == values) || (array == null && values == null))
			return true;
		else if ((array == null && values != null)
				|| (array != null && values == null))
			return false;
		else if (array.length != values.length)
			return false;

		return equals(values, 0, array, 0, values.length);
	}

	public static boolean equals(byte[] values, int valuesIndex, byte[] array,
			int arrayIndex, int length) throws IllegalArgumentException {
		// Short-circuit in simple cases.
		if (array == values)
			return true;
		else if ((array == null && values != null)
				|| (array != null && values == null))
			return false;

		/*
		 * Check all the arguments. Seems lengthy, but it's fast and allows the
		 * code below it to be simpler. We have to do these sanity checks at
		 * SOME regardless.
		 */
		if (arrayIndex < 0 || valuesIndex < 0 || length < 0
				|| (arrayIndex + length) > array.length
				|| (valuesIndex + length) > values.length)
			throw new IllegalArgumentException("sourceIndex [" + arrayIndex
					+ "] and valuesIndex [" + valuesIndex
					+ "] must be >= 0. length [" + length
					+ "] must be >= 0. (sourceIndex + length) ["
					+ (arrayIndex + length) + "] must be <= source.length ["
					+ array.length + "]. (valuesIndex + length) ["
					+ (valuesIndex + length) + "]  must be <= values.length ["
					+ values.length + "].");

		for (int i = 0; i < length; i++) {
			if (array[arrayIndex + i] != values[valuesIndex + i])
				return false;
		}

		return true;
	}

	/*
	 * ========================================================================
	 * byte[] array, byte value
	 * ========================================================================
	 */
	public static int indexOf(byte value, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(value, array, 0, array.length);
	}

	public static int indexOf(byte value, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(value, array, index, array.length - index);
	}

	public static int indexOf(byte value, byte[] array, int index, int length)
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
	public static int indexOf(byte[] values, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(values, array, 0, array.length);
	}

	public static int indexOf(byte[] values, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(values, array, index, array.length - index);
	}

	public static int indexOf(byte[] values, byte[] array, int index, int length)
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
	public static int indexOfAny(byte[] values, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(values, array, 0, array.length);
	}

	public static int indexOfAny(byte[] values, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(values, array, index, array.length - index);
	}

	public static int indexOfAny(byte[] values, byte[] array, int index,
			int length) throws IllegalArgumentException {
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
	public static int lastIndexOf(byte value, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(value, array, 0, array.length);
	}

	public static int lastIndexOf(byte value, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(value, array, index, array.length - index);
	}

	public static int lastIndexOf(byte value, byte[] array, int index,
			int length) throws IllegalArgumentException {
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
	public static int lastIndexOf(byte[] values, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(values, array, 0, array.length);
	}

	public static int lastIndexOf(byte[] values, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(values, array, index, array.length - index);
	}

	public static int lastIndexOf(byte[] values, byte[] array, int index,
			int length) throws IllegalArgumentException {
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
	public static int lastIndexOfAny(byte[] values, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(values, array, 0, array.length);
	}

	public static int lastIndexOfAny(byte[] values, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(values, array, index, array.length - index);
	}

	public static int lastIndexOfAny(byte[] values, byte[] array, int index,
			int length) throws IllegalArgumentException {
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

	public static int firstIndexAfter(byte value, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return firstIndexAfter(value, array, 0, array.length);
	}

	public static int firstIndexAfter(byte value, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return firstIndexAfter(value, array, index, array.length - index);
	}

	public static int firstIndexAfter(byte value, byte[] array, int index,
			int length) throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");
		if (index < 0 || length < 0 || (index + length) > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0, length [" + length
					+ "] must be >= 0 and (index+length) [" + (index + length)
					+ "] must be < array.length [" + array.length + "]");

		for (int end = (index + length); index < end && array[index] == value; index++)
			;

		return index;
	}

	public static int firstIndexAfter(byte[] values, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return firstIndexAfter(values, array, 0, array.length);
	}

	public static int firstIndexAfter(byte[] values, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return firstIndexAfter(values, array, index, array.length - index);
	}

	public static int firstIndexAfter(byte[] values, byte[] array, int index,
			int length) throws IllegalArgumentException {
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

			/*
			 * If j is values.length, we matched all, so we need to keep going,
			 * otherwise return the index we found the non-match at.
			 */
			if (j == values.length)
				index += j;
			else
				return (index + j);
		}

		return index;
	}

	public static int firstIndexAfterAny(byte[] values, byte[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return firstIndexAfterAny(values, array, 0, array.length);
	}

	public static int firstIndexAfterAny(byte[] values, byte[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return firstIndexAfterAny(values, array, index, array.length - index);
	}

	public static int firstIndexAfterAny(byte[] values, byte[] array,
			int index, int length) throws IllegalArgumentException {
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
		boolean match;

		// Cycle over each source char
		for (int end = (index + length); index < end; index++) {
			byte b = array[index];

			/*
			 * Compare current char to our given values; if ANY match, stop
			 * comparing and move to the next char. If non match, return the
			 * index that occurred to the caller.
			 */
			for (match = false, j = 0; !match && j < values.length; j++) {
				if (b == values[j])
					match = true;
			}

			/*
			 * If there was no match, return the index that occurred at to the
			 * caller. If there WAS a match, cycle to the next char and try
			 * again.
			 */
			if (!match)
				return (index + j);
		}

		return index;
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
	public static char[] append(char[] values, char[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return insert(values, array, array.length);
	}

	public static char[] insert(char[] values, char[] array, int index)
			throws IllegalArgumentException {
		if (values == null || array == null)
			throw new IllegalArgumentException(
					"Neither values or array can be null.");
		if (index < 0 || index > array.length)
			throw new IllegalArgumentException("index [" + index
					+ "] must be >= 0 and <= array.length [" + array.length
					+ "]");

		// Remember the array's original length
		int originalLength = array.length;

		// Ensure the final size of the array
		array = ensureCapacity(array, array.length + values.length);

		// Move contents down to make room if this is an insert.
		if (index < array.length)
			System.arraycopy(array, index, array, index + values.length,
					originalLength - index);

		// Copy the values into the resulting array
		System.arraycopy(values, 0, array, index, values.length);

		return array;
	}

	public static char[] ensureCapacity(char[] array, int capacity) {
		return ensureCapacity(array, capacity, 1);
	}

	public static char[] ensureCapacity(char[] array, int capacity,
			float growthFactor) {
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

	public static boolean equals(char[] values, char[] array) {
		// Short-circuit in simple cases.
		if ((array == values) || (array == null && values == null))
			return true;
		else if ((array == null && values != null)
				|| (array != null && values == null))
			return false;
		else if (array.length != values.length)
			return false;

		return equals(values, 0, array, 0, values.length);
	}

	public static boolean equals(char[] values, int valuesIndex, char[] array,
			int arrayIndex, int length) throws IllegalArgumentException {
		// Short-circuit in simple cases.
		if (array == values)
			return true;
		else if ((array == null && values != null)
				|| (array != null && values == null))
			return false;

		/*
		 * Check all the arguments. Seems lengthy, but it's fast and allows the
		 * code below it to be simpler. We have to do these sanity checks at
		 * SOME regardless.
		 */
		if (arrayIndex < 0 || valuesIndex < 0 || length < 0
				|| (arrayIndex + length) > array.length
				|| (valuesIndex + length) > values.length)
			throw new IllegalArgumentException("sourceIndex [" + arrayIndex
					+ "] and valuesIndex [" + valuesIndex
					+ "] must be >= 0. length [" + length
					+ "] must be >= 0. (sourceIndex + length) ["
					+ (arrayIndex + length) + "] must be <= source.length ["
					+ array.length + "]. (valuesIndex + length) ["
					+ (valuesIndex + length) + "]  must be <= values.length ["
					+ values.length + "].");

		for (int i = 0; i < length; i++) {
			if (array[arrayIndex + i] != values[valuesIndex + i])
				return false;
		}

		return true;
	}

	/*
	 * ========================================================================
	 * char[] array, char value
	 * ========================================================================
	 */
	public static int indexOf(char value, char[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(value, array, 0, array.length);
	}

	public static int indexOf(char value, char[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(value, array, index, array.length - index);
	}

	public static int indexOf(char value, char[] array, int index, int length)
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
	public static int indexOf(char[] values, char[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(values, array, 0, array.length);
	}

	public static int indexOf(char[] values, char[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(values, array, index, array.length - index);
	}

	public static int indexOf(char[] values, char[] array, int index, int length)
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
	public static int indexOfAny(char[] values, char[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(values, array, 0, array.length);
	}

	public static int indexOfAny(char[] values, char[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOfAny(values, array, index, array.length - index);
	}

	public static int indexOfAny(char[] values, char[] array, int index,
			int length) throws IllegalArgumentException {
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
	public static int lastIndexOf(char value, char[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(value, array, 0, array.length);
	}

	public static int lastIndexOf(char value, char[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return indexOf(value, array, index, array.length - index);
	}

	public static int lastIndexOf(char value, char[] array, int index,
			int length) throws IllegalArgumentException {
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
	public static int lastIndexOf(char[] values, char[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(values, array, 0, array.length);
	}

	public static int lastIndexOf(char[] values, char[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOf(values, array, index, array.length - index);
	}

	public static int lastIndexOf(char[] values, char[] array, int index,
			int length) throws IllegalArgumentException {
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
	public static int lastIndexOfAny(char[] values, char[] array)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(values, array, 0, array.length);
	}

	public static int lastIndexOfAny(char[] values, char[] array, int index)
			throws IllegalArgumentException {
		if (array == null)
			throw new IllegalArgumentException("array cannot be null");

		return lastIndexOfAny(values, array, index, array.length - index);
	}

	public static int lastIndexOfAny(char[] values, char[] array, int index,
			int length) throws IllegalArgumentException {
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