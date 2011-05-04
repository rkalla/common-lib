package com.thebuzzmedia.common.util;

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
		int j = 0;

		// Loop until we get within values.length units before the end
		for (int end = (index + length - values.length); index <= end; index++) {
			// Increment j as long as we can match values in-order
			for (j = 0; j < values.length && values[j] == array[index + j]; j++)
				;

			/*
			 * j will only get incremented to values.length if all the values
			 * were matched, in-order, in which case 'index' is what we return.
			 * Otherwise skip ahead by the number of matched values and start
			 * trying to match again.
			 */
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
		int j = 0;

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

	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte[] values
	 * ========================================================================
	 */

	/*
	 * ========================================================================
	 * REVERSE byte[] array, ANY byte[] values
	 * ========================================================================
	 */
}