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

public class ArrayUtilsTest {
	public static final byte[] BARRAY = { -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5 };

	public static final byte[] BVALUE_N5 = { -5 };
	public static final byte[] BVALUE_0 = { 0 };
	public static final byte[] BVALUE_5 = { 5 };

	public static final byte[] BVALUES_NEG = { -5, -4, -3, -2, -1 };
	public static final byte[] BVALUES_ZERO = { 0 };
	public static final byte[] BVALUES_POS = { 1, 2, 3, 4, 5 };

	public static final char[] CARRAY = { 'a', 'b', 'c', 'd', 'e', '0', '1',
			'2', '3', '4', '5' };

	public static final char[] CVALUE_N5 = { 'a' };
	public static final char[] CVALUE_0 = { '0' };
	public static final char[] CVALUE_5 = { '5' };

	public static final char[] CVALUES_NEG = { 'a', 'b', 'c', 'd', 'e' };
	public static final char[] CVALUES_ZERO = { '0' };
	public static final char[] CVALUES_POS = { '1', '2', '3', '4', '5' };

	@Test
	public void testAppendBaBa() {
		try {
			ArrayUtils.append((byte[]) null, (byte[]) null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.append((byte[]) null, BARRAY);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.append(BARRAY, (byte[]) null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		byte[] array = new byte[0];

		array = ArrayUtils.append(BVALUES_NEG, array);
		array = ArrayUtils.append(BVALUES_ZERO, array);
		array = ArrayUtils.append(BVALUES_POS, array);

		assertEquals(BARRAY.length, array.length);
		assertTrue(ArrayUtils.equals(BARRAY, array));
	}

	@Test
	public void testInsertBaBaI() {
		try {
			ArrayUtils.insert((byte[]) null, (byte[]) null, 0);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.insert((byte[]) null, BARRAY, 0);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.insert(BARRAY, (byte[]) null, 0);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		byte[] array = new byte[0];

		array = ArrayUtils.insert(BVALUES_NEG, array, 0);
		array = ArrayUtils.insert(BVALUES_ZERO, array, 0);
		array = ArrayUtils.insert(BVALUES_POS, array, 0);

		assertEquals(BARRAY.length, array.length);

		assertTrue(ArrayUtils.equals(BVALUES_POS, 0, array, 0,
				BVALUES_POS.length));
		assertTrue(ArrayUtils.equals(BVALUES_ZERO, 0, array,
				BVALUES_POS.length, BVALUES_ZERO.length));
		assertTrue(ArrayUtils.equals(BVALUES_NEG, 0, array, BVALUES_POS.length
				+ BVALUES_ZERO.length, BVALUES_NEG.length));
	}

	@Test
	public void testEnsureCapacityIBa() {
		assertEquals(BARRAY,
				ArrayUtils.ensureCapacity(BARRAY, BARRAY.length - 1));
		assertNotSame(BARRAY, ArrayUtils.ensureCapacity(BARRAY, BARRAY.length));

		byte[] array = ArrayUtils.ensureCapacity(BARRAY, BARRAY.length);
		assertEquals(BARRAY.length, array.length);
		assertTrue(ArrayUtils.equals(BARRAY, 0, array, 0, BARRAY.length));
	}

	@Test
	public void testEnsureCapacityIFBa() {
		assertEquals(BARRAY,
				ArrayUtils.ensureCapacity(BARRAY, BARRAY.length - 1, 2));
		assertNotSame(BARRAY,
				ArrayUtils.ensureCapacity(BARRAY, BARRAY.length, 2));

		byte[] array = ArrayUtils.ensureCapacity(BARRAY, BARRAY.length, 2);
		assertEquals(BARRAY.length * 2, array.length);
		assertTrue(ArrayUtils.equals(BARRAY, 0, array, 0, BARRAY.length));
	}

	/*
	 * ========================================================================
	 * byte[] array, byte value
	 * ========================================================================
	 */

	@Test
	public void testEqualsBaBa() {
		assertFalse(ArrayUtils.equals(BARRAY, (byte[]) null));
		assertFalse(ArrayUtils.equals((byte[]) null, BARRAY));
		assertFalse(ArrayUtils.equals(BARRAY, BVALUES_POS));
	
		assertTrue(ArrayUtils.equals((byte[]) null, (byte[]) null));
		assertTrue(ArrayUtils.equals(BARRAY, BARRAY));
	
		byte[] copy = new byte[BARRAY.length];
		System.arraycopy(BARRAY, 0, copy, 0, BARRAY.length);
	
		assertTrue(ArrayUtils.equals(copy, BARRAY));
	}

	@Test
	public void testEqualsIBaIBaI() {
		assertFalse(ArrayUtils.equals(null, 0, BARRAY, 0, BARRAY.length));
		assertFalse(ArrayUtils.equals(BARRAY, 0, null, 0, BARRAY.length));
	
		assertTrue(ArrayUtils.equals(BARRAY, 0, BARRAY, 0, BARRAY.length));
		assertTrue(ArrayUtils.equals(BVALUES_NEG, 0, BARRAY, 0,
				BVALUES_NEG.length));
		assertTrue(ArrayUtils.equals(BVALUES_POS, 0, BARRAY,
				BVALUES_NEG.length + 1, BVALUES_POS.length));
	}

	@Test
	public void testIndexOfBaB() {
		try {
			ArrayUtils.indexOf((byte) 0, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf((byte) -5, BARRAY));
		assertEquals(5, ArrayUtils.indexOf((byte) 0, BARRAY));
		assertEquals(10, ArrayUtils.indexOf((byte) 5, BARRAY));
	}

	@Test
	public void testIndexOfIBaB() {
		try {
			ArrayUtils.indexOf((byte) 0, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf((byte) 0, BARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf((byte) -5, BARRAY, 0));
		assertEquals(5, ArrayUtils.indexOf((byte) 0, BARRAY, 0));
		assertEquals(10, ArrayUtils.indexOf((byte) 5, BARRAY, 0));

		assertEquals(-1, ArrayUtils.indexOf((byte) -5, BARRAY, 5));
		assertEquals(5, ArrayUtils.indexOf((byte) 0, BARRAY, 5));
		assertEquals(10, ArrayUtils.indexOf((byte) 5, BARRAY, 5));

		assertEquals(-1, ArrayUtils.indexOf((byte) -5, BARRAY, 10));
		assertEquals(-1, ArrayUtils.indexOf((byte) 0, BARRAY, 10));
		assertEquals(10, ArrayUtils.indexOf((byte) 5, BARRAY, 10));
	}

	@Test
	public void testIndexOfIIBaB() {
		try {
			ArrayUtils.indexOf((byte) 0, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf((byte) 0, BARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf((byte) 0, BARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf((byte) 0, BARRAY, 0, BARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf((byte) -5, BARRAY, 0, BARRAY.length));
		assertEquals(5, ArrayUtils.indexOf((byte) 0, BARRAY, 0, BARRAY.length));
		assertEquals(10, ArrayUtils.indexOf((byte) 5, BARRAY, 0, BARRAY.length));

		assertEquals(-1,
				ArrayUtils.indexOf((byte) -5, BARRAY, 5, BARRAY.length - 5));
		assertEquals(5,
				ArrayUtils.indexOf((byte) 0, BARRAY, 5, BARRAY.length - 5));
		assertEquals(10,
				ArrayUtils.indexOf((byte) 5, BARRAY, 5, BARRAY.length - 5));

		assertEquals(-1, ArrayUtils.indexOf((byte) -5, BARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.indexOf((byte) 0, BARRAY, 10, 1));
		assertEquals(10, ArrayUtils.indexOf((byte) 5, BARRAY, 10, 1));
	}

	/*
	 * ========================================================================
	 * byte[] array, byte[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfBaBa() {
		try {
			ArrayUtils.indexOf(BVALUES_ZERO, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(BVALUES_NEG, BARRAY));
		assertEquals(5, ArrayUtils.indexOf(BVALUES_ZERO, BARRAY));
		assertEquals(6, ArrayUtils.indexOf(BVALUES_POS, BARRAY));
	}

	@Test
	public void testIndexOfIBaBa() {
		try {
			ArrayUtils.indexOf(BVALUES_ZERO, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(BVALUES_NEG, BARRAY, 0));
		assertEquals(5, ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 0));
		assertEquals(6, ArrayUtils.indexOf(BVALUES_POS, BARRAY, 0));

		assertEquals(-1, ArrayUtils.indexOf(BVALUES_NEG, BARRAY, 5));
		assertEquals(5, ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 5));
		assertEquals(6, ArrayUtils.indexOf(BVALUES_POS, BARRAY, 6));

		assertEquals(-1, ArrayUtils.indexOf(BVALUES_NEG, BARRAY, 6));
		assertEquals(-1, ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 10));
	}

	@Test
	public void testIndexOfIIBaBa() {
		try {
			ArrayUtils.indexOf(BVALUES_ZERO, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 0, BARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOf(BVALUES_NEG, BARRAY, 0, BARRAY.length));
		assertEquals(5,
				ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 0, BARRAY.length));
		assertEquals(6,
				ArrayUtils.indexOf(BVALUES_POS, BARRAY, 0, BARRAY.length));

		assertEquals(-1,
				ArrayUtils.indexOf(BVALUES_NEG, BARRAY, 5, BARRAY.length - 5));
		assertEquals(5,
				ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 5, BARRAY.length - 5));
		assertEquals(6,
				ArrayUtils.indexOf(BVALUES_POS, BARRAY, 5, BARRAY.length - 5));

		assertEquals(-1, ArrayUtils.indexOf(BVALUES_NEG, BARRAY, 6, 5));
		assertEquals(5, ArrayUtils.indexOf(BVALUES_ZERO, BARRAY, 5, 1));
		assertEquals(-1, ArrayUtils.indexOf(BVALUES_POS, BARRAY, 2, 5));
	}

	/*
	 * ========================================================================
	 * byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfAnyBaBa() {
		try {
			ArrayUtils.indexOfAny(BVALUE_0, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(BVALUE_N5, BARRAY));
		assertEquals(5, ArrayUtils.indexOfAny(BVALUE_0, BARRAY));
		assertEquals(10, ArrayUtils.indexOfAny(BVALUE_5, BARRAY));
	}

	@Test
	public void testIndexOfAnyIBaBa() {
		try {
			ArrayUtils.indexOfAny(BVALUE_0, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(BVALUE_0, BARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(BVALUE_N5, BARRAY, 0));
		assertEquals(5, ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 0));
		assertEquals(10, ArrayUtils.indexOfAny(BVALUE_5, BARRAY, 0));

		assertEquals(-1, ArrayUtils.indexOfAny(BVALUE_N5, BARRAY, 5));
		assertEquals(5, ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 5));
		assertEquals(10, ArrayUtils.indexOfAny(BVALUE_5, BARRAY, 5));

		assertEquals(-1, ArrayUtils.indexOfAny(BVALUE_N5, BARRAY, 10));
		assertEquals(-1, ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 10));
		assertEquals(10, ArrayUtils.indexOfAny(BVALUE_5, BARRAY, 10));
	}

	@Test
	public void testIndexOfAnyIIBaBa() {
		try {
			ArrayUtils.indexOfAny(BVALUE_0, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(BVALUE_0, BARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 0, BARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOfAny(BVALUE_N5, BARRAY, 0, BARRAY.length));
		assertEquals(5,
				ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 0, BARRAY.length));
		assertEquals(10,
				ArrayUtils.indexOfAny(BVALUE_5, BARRAY, 0, BARRAY.length));

		assertEquals(-1,
				ArrayUtils.indexOfAny(BVALUE_N5, BARRAY, 5, BARRAY.length - 5));
		assertEquals(5,
				ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 5, BARRAY.length - 5));
		assertEquals(10,
				ArrayUtils.indexOfAny(BVALUE_5, BARRAY, 5, BARRAY.length - 5));

		assertEquals(-1, ArrayUtils.indexOfAny(BVALUE_N5, BARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.indexOfAny(BVALUE_0, BARRAY, 10, 1));
		assertEquals(10, ArrayUtils.indexOfAny(BVALUE_5, BARRAY, 10, 1));
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte value
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfBaB() {
		try {
			ArrayUtils.lastIndexOf((byte) 0, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf((byte) -5, BARRAY));
		assertEquals(5, ArrayUtils.lastIndexOf((byte) 0, BARRAY));
		assertEquals(10, ArrayUtils.lastIndexOf((byte) 5, BARRAY));
	}

	@Test
	public void testLastIndexOfIBaB() {
		try {
			ArrayUtils.lastIndexOf((byte) 0, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf((byte) 0, BARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf((byte) -5, BARRAY, 0));
		assertEquals(5, ArrayUtils.lastIndexOf((byte) 0, BARRAY, 0));
		assertEquals(10, ArrayUtils.lastIndexOf((byte) 5, BARRAY, 0));

		assertEquals(-1, ArrayUtils.lastIndexOf((byte) -5, BARRAY, 5));
		assertEquals(5, ArrayUtils.lastIndexOf((byte) 0, BARRAY, 5));
		assertEquals(10, ArrayUtils.lastIndexOf((byte) 5, BARRAY, 5));

		assertEquals(-1, ArrayUtils.lastIndexOf((byte) -5, BARRAY, 10));
		assertEquals(-1, ArrayUtils.lastIndexOf((byte) 0, BARRAY, 10));
		assertEquals(10, ArrayUtils.lastIndexOf((byte) 5, BARRAY, 10));
	}

	@Test
	public void testLastIndexOfIIBaB() {
		try {
			ArrayUtils.lastIndexOf((byte) 0, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf((byte) 0, BARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf((byte) 0, BARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf((byte) 0, BARRAY, 0, BARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOf((byte) -5, BARRAY, 0, BARRAY.length));
		assertEquals(5,
				ArrayUtils.lastIndexOf((byte) 0, BARRAY, 0, BARRAY.length));
		assertEquals(10,
				ArrayUtils.lastIndexOf((byte) 5, BARRAY, 0, BARRAY.length));

		assertEquals(-1,
				ArrayUtils.lastIndexOf((byte) -5, BARRAY, 5, BARRAY.length - 5));
		assertEquals(5,
				ArrayUtils.lastIndexOf((byte) 0, BARRAY, 5, BARRAY.length - 5));
		assertEquals(10,
				ArrayUtils.lastIndexOf((byte) 5, BARRAY, 5, BARRAY.length - 5));

		assertEquals(-1, ArrayUtils.lastIndexOf((byte) -5, BARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.lastIndexOf((byte) 0, BARRAY, 10, 1));
		assertEquals(10, ArrayUtils.lastIndexOf((byte) 5, BARRAY, 10, 1));
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfBaBa() {
		try {
			ArrayUtils.lastIndexOf(BVALUES_ZERO, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(BVALUES_NEG, BARRAY));
		assertEquals(5, ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY));
		assertEquals(6, ArrayUtils.lastIndexOf(BVALUES_POS, BARRAY));
	}

	@Test
	public void testLastIndexOfIBaBa() {
		try {
			ArrayUtils.lastIndexOf(BVALUES_ZERO, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(BVALUES_NEG, BARRAY, 0));
		assertEquals(5, ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 0));
		assertEquals(6, ArrayUtils.lastIndexOf(BVALUES_POS, BARRAY, 0));

		assertEquals(-1, ArrayUtils.lastIndexOf(BVALUES_NEG, BARRAY, 5));
		assertEquals(5, ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 5));
		assertEquals(6, ArrayUtils.lastIndexOf(BVALUES_POS, BARRAY, 6));

		assertEquals(-1, ArrayUtils.lastIndexOf(BVALUES_NEG, BARRAY, 6));
		assertEquals(-1, ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 10));
	}

	@Test
	public void testLastIndexOfIIBaBa() {
		try {
			ArrayUtils.lastIndexOf(BVALUES_ZERO, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 0, BARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOf(BVALUES_NEG, BARRAY, 0, BARRAY.length));
		assertEquals(5,
				ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 0, BARRAY.length));
		assertEquals(6,
				ArrayUtils.lastIndexOf(BVALUES_POS, BARRAY, 0, BARRAY.length));

		assertEquals(-1, ArrayUtils.lastIndexOf(BVALUES_NEG, BARRAY, 5,
				BARRAY.length - 5));
		assertEquals(5, ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 5,
				BARRAY.length - 5));
		assertEquals(6, ArrayUtils.lastIndexOf(BVALUES_POS, BARRAY, 5,
				BARRAY.length - 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(BVALUES_NEG, BARRAY, 6, 5));
		assertEquals(5, ArrayUtils.lastIndexOf(BVALUES_ZERO, BARRAY, 5, 1));
		assertEquals(-1, ArrayUtils.lastIndexOf(BVALUES_POS, BARRAY, 2, 5));
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfAnyBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(BVALUE_0, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(BVALUE_N5, BARRAY));
		assertEquals(5, ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY));
		assertEquals(10, ArrayUtils.lastIndexOfAny(BVALUE_5, BARRAY));
	}

	@Test
	public void testLastIndexOfAnyIBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(BVALUE_0, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(BVALUE_N5, BARRAY, 0));
		assertEquals(5, ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(BVALUE_5, BARRAY, 0));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(BVALUE_N5, BARRAY, 5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 5));
		assertEquals(10, ArrayUtils.lastIndexOfAny(BVALUE_5, BARRAY, 5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(BVALUE_N5, BARRAY, 10));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 10));
		assertEquals(10, ArrayUtils.lastIndexOfAny(BVALUE_5, BARRAY, 10));
	}

	@Test
	public void testLastIndexOfAnyIIBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(BVALUE_0, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 0, BARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOfAny(BVALUE_N5, BARRAY, 0, BARRAY.length));
		assertEquals(5,
				ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 0, BARRAY.length));
		assertEquals(10,
				ArrayUtils.lastIndexOfAny(BVALUE_5, BARRAY, 0, BARRAY.length));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(BVALUE_N5, BARRAY, 5,
				BARRAY.length - 5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 5,
				BARRAY.length - 5));
		assertEquals(10, ArrayUtils.lastIndexOfAny(BVALUE_5, BARRAY, 5,
				BARRAY.length - 5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(BVALUE_N5, BARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(BVALUE_0, BARRAY, 10, 1));
		assertEquals(10, ArrayUtils.lastIndexOfAny(BVALUE_5, BARRAY, 10, 1));
	}
	
	@Test
	public void testFirstIndexAfterBaB() {
		assertEquals(0, ArrayUtils.firstIndexAfter(Byte.MAX_VALUE, BARRAY));
		assertEquals(1, ArrayUtils.firstIndexAfter((byte)-5, BARRAY));
	}
	
	@Test
	public void testFirstIndexAfterIBaB() {
		assertEquals(0, ArrayUtils.firstIndexAfter(Byte.MAX_VALUE, BARRAY, 0));
		assertEquals(1, ArrayUtils.firstIndexAfter((byte)-5, BARRAY, 0));
		assertEquals(6, ArrayUtils.firstIndexAfter((byte)0, BARRAY, 5));
		assertEquals(11, ArrayUtils.firstIndexAfter((byte)5, BARRAY, 10));
	}
	
	@Test
	public void testFirstIndexAfterIIBaB() {
		assertEquals(0, ArrayUtils.firstIndexAfter(Byte.MAX_VALUE, BARRAY, 0, BARRAY.length));
		assertEquals(1, ArrayUtils.firstIndexAfter((byte)-5, BARRAY, 0, 1));
		assertEquals(6, ArrayUtils.firstIndexAfter((byte)0, BARRAY, 5, 1));
		assertEquals(11, ArrayUtils.firstIndexAfter((byte)5, BARRAY, 10, 1));
		
		assertEquals(1, ArrayUtils.firstIndexAfter((byte)-5, BARRAY, 0, 10));
		assertEquals(6, ArrayUtils.firstIndexAfter((byte)0, BARRAY, 5, 5));
		assertEquals(11, ArrayUtils.firstIndexAfter((byte)5, BARRAY, 10, 1));
		
		try {
			ArrayUtils.firstIndexAfter((byte)0, BARRAY, 5, 20);
			assertTrue(false);
		} catch(Exception e) {
			assertTrue(true);
		}
	}

	/* ############################################################## */
	/* ############################################################## */
	/* ############################################################## */
	/* ############################################################## */
	/* ############################################################## */
	/* ############################################################## */
	/* ############################################################## */
	/* ############################################################## */

	@Test
	public void testAppendCaCa() {
		try {
			ArrayUtils.append((char[]) null, (char[]) null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.append((char[]) null, CARRAY);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.append(CARRAY, (char[]) null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		char[] array = new char[0];

		array = ArrayUtils.append(CVALUES_NEG, array);
		array = ArrayUtils.append(CVALUES_ZERO, array);
		array = ArrayUtils.append(CVALUES_POS, array);

		assertEquals(CARRAY.length, array.length);
		assertTrue(ArrayUtils.equals(CARRAY, array));
	}

	@Test
	public void testInsertCaCaI() {
		try {
			ArrayUtils.insert((char[]) null, (char[]) null, 0);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.insert((char[]) null, CARRAY, 0);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.insert(CARRAY, (char[]) null, 0);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		char[] array = new char[0];

		array = ArrayUtils.insert(CVALUES_NEG, array, 0);
		array = ArrayUtils.insert(CVALUES_ZERO, array, 0);
		array = ArrayUtils.insert(CVALUES_POS, array, 0);

		assertEquals(CARRAY.length, array.length);

		assertTrue(ArrayUtils.equals(CVALUES_POS, 0, array, 0,
				CVALUES_POS.length));
		assertTrue(ArrayUtils.equals(CVALUES_ZERO, 0, array,
				CVALUES_POS.length, CVALUES_ZERO.length));
		assertTrue(ArrayUtils.equals(CVALUES_NEG, 0, array, CVALUES_POS.length
				+ CVALUES_ZERO.length, CVALUES_NEG.length));
	}

	@Test
	public void testEnsureCapacityICa() {
		assertEquals(CARRAY,
				ArrayUtils.ensureCapacity(CARRAY, CARRAY.length - 1));
		assertNotSame(CARRAY, ArrayUtils.ensureCapacity(CARRAY, CARRAY.length));

		char[] array = ArrayUtils.ensureCapacity(CARRAY, CARRAY.length);
		assertEquals(CARRAY.length, array.length);
		assertTrue(ArrayUtils.equals(CARRAY, 0, array, 0, CARRAY.length));
	}

	@Test
	public void testEnsureCapacityIFCa() {
		assertEquals(CARRAY,
				ArrayUtils.ensureCapacity(CARRAY, CARRAY.length - 1, 2));
		assertNotSame(CARRAY,
				ArrayUtils.ensureCapacity(CARRAY, CARRAY.length, 2));

		char[] array = ArrayUtils.ensureCapacity(CARRAY, CARRAY.length, 2);
		assertEquals(CARRAY.length * 2, array.length);
		assertTrue(ArrayUtils.equals(CARRAY, 0, array, 0, CARRAY.length));
	}

	@Test
	public void testEqualsCaCa() {
		assertFalse(ArrayUtils.equals(CARRAY, (char[]) null));
		assertFalse(ArrayUtils.equals((char[]) null, CARRAY));
		assertFalse(ArrayUtils.equals(CARRAY, CVALUES_POS));
	
		assertTrue(ArrayUtils.equals((char[]) null, (char[]) null));
		assertTrue(ArrayUtils.equals(CARRAY, CARRAY));
	
		char[] copy = new char[CARRAY.length];
		System.arraycopy(CARRAY, 0, copy, 0, CARRAY.length);
	
		assertTrue(ArrayUtils.equals(copy, CARRAY));
	}

	@Test
	public void testEqualsICaICaI() {
		assertFalse(ArrayUtils.equals(null, 0, CARRAY, 0, CARRAY.length));
		assertFalse(ArrayUtils.equals(CARRAY, 0, null, 0, CARRAY.length));
	
		assertTrue(ArrayUtils.equals(CARRAY, 0, CARRAY, 0, CARRAY.length));
		assertTrue(ArrayUtils.equals(CVALUES_NEG, 0, CARRAY, 0,
				CVALUES_NEG.length));
		assertTrue(ArrayUtils.equals(CVALUES_POS, 0, CARRAY,
				CVALUES_NEG.length + 1, CVALUES_POS.length));
	}

	/*
	 * ========================================================================
	 * char[] array, char value
	 * ========================================================================
	 */
	@Test
	public void testIndexOfCaC() {
		try {
			ArrayUtils.indexOf((char) 0, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf('a', CARRAY));
		assertEquals(5, ArrayUtils.indexOf('0', CARRAY));
		assertEquals(10, ArrayUtils.indexOf('5', CARRAY));
	}

	@Test
	public void testIndexOfICaC() {
		try {
			ArrayUtils.indexOf('0', null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf('0', CARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf('a', CARRAY, 0));
		assertEquals(5, ArrayUtils.indexOf('0', CARRAY, 0));
		assertEquals(10, ArrayUtils.indexOf('5', CARRAY, 0));

		assertEquals(-1, ArrayUtils.indexOf('a', CARRAY, 5));
		assertEquals(5, ArrayUtils.indexOf('0', CARRAY, 5));
		assertEquals(10, ArrayUtils.indexOf('5', CARRAY, 5));

		assertEquals(-1, ArrayUtils.indexOf('a', CARRAY, 10));
		assertEquals(-1, ArrayUtils.indexOf('0', CARRAY, 10));
		assertEquals(10, ArrayUtils.indexOf('5', CARRAY, 10));
	}

	@Test
	public void testIndexOfIICaC() {
		try {
			ArrayUtils.indexOf('0', null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf('0', CARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf('0', CARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf('0', CARRAY, 0, CARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf('a', CARRAY, 0, CARRAY.length));
		assertEquals(5, ArrayUtils.indexOf('0', CARRAY, 0, CARRAY.length));
		assertEquals(10, ArrayUtils.indexOf('5', CARRAY, 0, CARRAY.length));

		assertEquals(-1, ArrayUtils.indexOf('a', CARRAY, 5, CARRAY.length - 5));
		assertEquals(5, ArrayUtils.indexOf('0', CARRAY, 5, CARRAY.length - 5));
		assertEquals(10, ArrayUtils.indexOf('5', CARRAY, 5, CARRAY.length - 5));

		assertEquals(-1, ArrayUtils.indexOf('a', CARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.indexOf('0', CARRAY, 10, 1));
		assertEquals(10, ArrayUtils.indexOf('5', CARRAY, 10, 1));
	}

	/*
	 * ========================================================================
	 * char[] array, char[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfCaCa() {
		try {
			ArrayUtils.indexOf(CVALUES_ZERO, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(CVALUES_NEG, CARRAY));
		assertEquals(5, ArrayUtils.indexOf(CVALUES_ZERO, CARRAY));
		assertEquals(6, ArrayUtils.indexOf(CVALUES_POS, CARRAY));
	}

	@Test
	public void testIndexOfICaCa() {
		try {
			ArrayUtils.indexOf(CVALUES_ZERO, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(CVALUES_NEG, CARRAY, 0));
		assertEquals(5, ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 0));
		assertEquals(6, ArrayUtils.indexOf(CVALUES_POS, CARRAY, 0));

		assertEquals(-1, ArrayUtils.indexOf(CVALUES_NEG, CARRAY, 5));
		assertEquals(5, ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 5));
		assertEquals(6, ArrayUtils.indexOf(CVALUES_POS, CARRAY, 6));

		assertEquals(-1, ArrayUtils.indexOf(CVALUES_NEG, CARRAY, 6));
		assertEquals(-1, ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 10));
	}

	@Test
	public void testIndexOfIICaCa() {
		try {
			ArrayUtils.indexOf(CVALUES_ZERO, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 0, CARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOf(CVALUES_NEG, CARRAY, 0, CARRAY.length));
		assertEquals(5,
				ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 0, CARRAY.length));
		assertEquals(6,
				ArrayUtils.indexOf(CVALUES_POS, CARRAY, 0, CARRAY.length));

		assertEquals(-1,
				ArrayUtils.indexOf(CVALUES_NEG, CARRAY, 5, CARRAY.length - 5));
		assertEquals(5,
				ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 5, CARRAY.length - 5));
		assertEquals(6,
				ArrayUtils.indexOf(CVALUES_POS, CARRAY, 5, CARRAY.length - 5));

		assertEquals(-1, ArrayUtils.indexOf(CVALUES_NEG, CARRAY, 6, 5));
		assertEquals(5, ArrayUtils.indexOf(CVALUES_ZERO, CARRAY, 5, 1));
		assertEquals(-1, ArrayUtils.indexOf(CVALUES_POS, CARRAY, 2, 5));
	}

	/*
	 * ========================================================================
	 * char[] array, ANY char[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfAnyCaCa() {
		try {
			ArrayUtils.indexOfAny(CVALUE_0, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(CVALUE_N5, CARRAY));
		assertEquals(5, ArrayUtils.indexOfAny(CVALUE_0, CARRAY));
		assertEquals(10, ArrayUtils.indexOfAny(CVALUE_5, CARRAY));
	}

	@Test
	public void testIndexOfAnyICaCa() {
		try {
			ArrayUtils.indexOfAny(CVALUE_0, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(CVALUE_0, CARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(CVALUE_N5, CARRAY, 0));
		assertEquals(5, ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 0));
		assertEquals(10, ArrayUtils.indexOfAny(CVALUE_5, CARRAY, 0));

		assertEquals(-1, ArrayUtils.indexOfAny(CVALUE_N5, CARRAY, 5));
		assertEquals(5, ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 5));
		assertEquals(10, ArrayUtils.indexOfAny(CVALUE_5, CARRAY, 5));

		assertEquals(-1, ArrayUtils.indexOfAny(CVALUE_N5, CARRAY, 10));
		assertEquals(-1, ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 10));
		assertEquals(10, ArrayUtils.indexOfAny(CVALUE_5, CARRAY, 10));
	}

	@Test
	public void testIndexOfAnyIICaCa() {
		try {
			ArrayUtils.indexOfAny(CVALUE_0, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(CVALUE_0, CARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 0, CARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOfAny(CVALUE_N5, CARRAY, 0, CARRAY.length));
		assertEquals(5,
				ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 0, CARRAY.length));
		assertEquals(10,
				ArrayUtils.indexOfAny(CVALUE_5, CARRAY, 0, CARRAY.length));

		assertEquals(-1,
				ArrayUtils.indexOfAny(CVALUE_N5, CARRAY, 5, CARRAY.length - 5));
		assertEquals(5,
				ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 5, CARRAY.length - 5));
		assertEquals(10,
				ArrayUtils.indexOfAny(CVALUE_5, CARRAY, 5, CARRAY.length - 5));

		assertEquals(-1, ArrayUtils.indexOfAny(CVALUE_N5, CARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.indexOfAny(CVALUE_0, CARRAY, 10, 1));
		assertEquals(10, ArrayUtils.indexOfAny(CVALUE_5, CARRAY, 10, 1));
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, char value
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfCaC() {
		try {
			ArrayUtils.lastIndexOf('0', null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf('a', CARRAY));
		assertEquals(5, ArrayUtils.lastIndexOf('0', CARRAY));
		assertEquals(10, ArrayUtils.lastIndexOf('5', CARRAY));
	}

	@Test
	public void testLastIndexOfICaC() {
		try {
			ArrayUtils.lastIndexOf('0', null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf('0', CARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf('a', CARRAY, 0));
		assertEquals(5, ArrayUtils.lastIndexOf('0', CARRAY, 0));
		assertEquals(10, ArrayUtils.lastIndexOf('5', CARRAY, 0));

		assertEquals(-1, ArrayUtils.lastIndexOf('a', CARRAY, 5));
		assertEquals(5, ArrayUtils.lastIndexOf('0', CARRAY, 5));
		assertEquals(10, ArrayUtils.lastIndexOf('5', CARRAY, 5));

		assertEquals(-1, ArrayUtils.lastIndexOf('a', CARRAY, 10));
		assertEquals(-1, ArrayUtils.lastIndexOf('0', CARRAY, 10));
		assertEquals(10, ArrayUtils.lastIndexOf('5', CARRAY, 10));
	}

	@Test
	public void testLastIndexOfIICaC() {
		try {
			ArrayUtils.lastIndexOf('0', null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf('0', CARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf('0', CARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf('0', CARRAY, 0, CARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf('a', CARRAY, 0, CARRAY.length));
		assertEquals(5, ArrayUtils.lastIndexOf('0', CARRAY, 0, CARRAY.length));
		assertEquals(10, ArrayUtils.lastIndexOf('5', CARRAY, 0, CARRAY.length));

		assertEquals(-1,
				ArrayUtils.lastIndexOf('a', CARRAY, 5, CARRAY.length - 5));
		assertEquals(5,
				ArrayUtils.lastIndexOf('0', CARRAY, 5, CARRAY.length - 5));
		assertEquals(10,
				ArrayUtils.lastIndexOf('5', CARRAY, 5, CARRAY.length - 5));

		assertEquals(-1, ArrayUtils.lastIndexOf('a', CARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.lastIndexOf('0', CARRAY, 10, 1));
		assertEquals(10, ArrayUtils.lastIndexOf('5', CARRAY, 10, 1));
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, char[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfCaCa() {
		try {
			ArrayUtils.lastIndexOf(CVALUES_ZERO, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(CVALUES_NEG, CARRAY));
		assertEquals(5, ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY));
		assertEquals(6, ArrayUtils.lastIndexOf(CVALUES_POS, CARRAY));
	}

	@Test
	public void testLastIndexOfICaCa() {
		try {
			ArrayUtils.lastIndexOf(CVALUES_ZERO, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(CVALUES_NEG, CARRAY, 0));
		assertEquals(5, ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 0));
		assertEquals(6, ArrayUtils.lastIndexOf(CVALUES_POS, CARRAY, 0));

		assertEquals(-1, ArrayUtils.lastIndexOf(CVALUES_NEG, CARRAY, 5));
		assertEquals(5, ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 5));
		assertEquals(6, ArrayUtils.lastIndexOf(CVALUES_POS, CARRAY, 6));

		assertEquals(-1, ArrayUtils.lastIndexOf(CVALUES_NEG, CARRAY, 6));
		assertEquals(-1, ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 10));
	}

	@Test
	public void testLastIndexOfIICaCa() {
		try {
			ArrayUtils.lastIndexOf(CVALUES_ZERO, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 0, CARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOf(CVALUES_NEG, CARRAY, 0, CARRAY.length));
		assertEquals(5,
				ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 0, CARRAY.length));
		assertEquals(6,
				ArrayUtils.lastIndexOf(CVALUES_POS, CARRAY, 0, CARRAY.length));

		assertEquals(-1, ArrayUtils.lastIndexOf(CVALUES_NEG, CARRAY, 5,
				CARRAY.length - 5));
		assertEquals(5, ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 5,
				CARRAY.length - 5));
		assertEquals(6, ArrayUtils.lastIndexOf(CVALUES_POS, CARRAY, 5,
				CARRAY.length - 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(CVALUES_NEG, CARRAY, 6, 5));
		assertEquals(5, ArrayUtils.lastIndexOf(CVALUES_ZERO, CARRAY, 5, 1));
		assertEquals(-1, ArrayUtils.lastIndexOf(CVALUES_POS, CARRAY, 2, 5));
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, ANY char[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfAnyCaCa() {
		try {
			ArrayUtils.lastIndexOfAny(CVALUE_0, null);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(CVALUE_N5, CARRAY));
		assertEquals(5, ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY));
		assertEquals(10, ArrayUtils.lastIndexOfAny(CVALUE_5, CARRAY));
	}

	@Test
	public void testLastIndexOfAnyICaCa() {
		try {
			ArrayUtils.lastIndexOfAny(CVALUE_0, null, 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(CVALUE_N5, CARRAY, 0));
		assertEquals(5, ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(CVALUE_5, CARRAY, 0));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(CVALUE_N5, CARRAY, 5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 5));
		assertEquals(10, ArrayUtils.lastIndexOfAny(CVALUE_5, CARRAY, 5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(CVALUE_N5, CARRAY, 10));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 10));
		assertEquals(10, ArrayUtils.lastIndexOfAny(CVALUE_5, CARRAY, 10));
	}

	@Test
	public void testLastIndexOfAnyIICaCa() {
		try {
			ArrayUtils.lastIndexOfAny(CVALUE_0, null, 0, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, -1, 1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 0, -1);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 0, CARRAY.length * 2);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOfAny(CVALUE_N5, CARRAY, 0, CARRAY.length));
		assertEquals(5,
				ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 0, CARRAY.length));
		assertEquals(10,
				ArrayUtils.lastIndexOfAny(CVALUE_5, CARRAY, 0, CARRAY.length));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(CVALUE_N5, CARRAY, 5,
				CARRAY.length - 5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 5,
				CARRAY.length - 5));
		assertEquals(10, ArrayUtils.lastIndexOfAny(CVALUE_5, CARRAY, 5,
				CARRAY.length - 5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(CVALUE_N5, CARRAY, 10, 1));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(CVALUE_0, CARRAY, 10, 1));
		assertEquals(10, ArrayUtils.lastIndexOfAny(CVALUE_5, CARRAY, 10, 1));
	}
	
	// TODO: Add tests for firstIndexAfter
	
}