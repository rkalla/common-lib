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

	/*
	 * ========================================================================
	 * byte[] array, byte value
	 * ========================================================================
	 */
	@Test
	public void testEqualsBaBa() {
		assertFalse(ArrayUtils.equals((byte[]) null, BARRAY));
		assertFalse(ArrayUtils.equals(BARRAY, (byte[]) null));
		assertFalse(ArrayUtils.equals(BVALUES_POS, BARRAY));

		assertTrue(ArrayUtils.equals((byte[]) null, (byte[]) null));
		assertTrue(ArrayUtils.equals(BARRAY, BARRAY));

		byte[] copy = new byte[BARRAY.length];
		System.arraycopy(BARRAY, 0, copy, 0, BARRAY.length);

		assertTrue(ArrayUtils.equals(BARRAY, copy));
	}

	@Test
	public void testEqualsIBaIBaI() {
		assertFalse(ArrayUtils.equals(0, BARRAY, 0, null, BARRAY.length));
		assertFalse(ArrayUtils.equals(0, null, 0, BARRAY, BARRAY.length));

		assertTrue(ArrayUtils.equals(0, BARRAY, 0, BARRAY, BARRAY.length));
		assertTrue(ArrayUtils.equals(0, BARRAY, 0, BVALUES_NEG,
				BVALUES_NEG.length));
		assertTrue(ArrayUtils.equals(BVALUES_NEG.length + 1, BARRAY, 0,
				BVALUES_POS, BVALUES_POS.length));
	}

	@Test
	public void testEnsureCapacityIBa() {
		assertEquals(BARRAY,
				ArrayUtils.ensureCapacity(BARRAY.length - 1, BARRAY));
		assertNotSame(BARRAY, ArrayUtils.ensureCapacity(BARRAY.length, BARRAY));

		byte[] array = ArrayUtils.ensureCapacity(BARRAY.length, BARRAY);
		assertEquals(BARRAY.length, array.length);
		assertTrue(ArrayUtils.equals(0, array, 0, BARRAY, BARRAY.length));
	}

	@Test
	public void testEnsureCapacityIFBa() {
		assertEquals(BARRAY,
				ArrayUtils.ensureCapacity(BARRAY.length - 1, 2, BARRAY));
		assertNotSame(BARRAY,
				ArrayUtils.ensureCapacity(BARRAY.length, 2, BARRAY));

		byte[] array = ArrayUtils.ensureCapacity(BARRAY.length, 2, BARRAY);
		assertEquals(BARRAY.length * 2, array.length);
		assertTrue(ArrayUtils.equals(0, array, 0, BARRAY, BARRAY.length));
	}

	@Test
	public void testIndexOfBaB() {
		try {
			ArrayUtils.indexOf(null, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(BARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(BARRAY, (byte) 5));
	}

	@Test
	public void testIndexOfIBaB() {
		try {
			ArrayUtils.indexOf(0, null, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, BARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(0, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(0, BARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.indexOf(5, BARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(5, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(5, BARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.indexOf(10, BARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.indexOf(10, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(10, BARRAY, (byte) 5));
	}

	@Test
	public void testIndexOfIIBaB() {
		try {
			ArrayUtils.indexOf(0, 1, null, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, 1, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, -1, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, BARRAY.length * 2, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, BARRAY.length, BARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(0, BARRAY.length, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(0, BARRAY.length, BARRAY, (byte) 5));

		assertEquals(-1,
				ArrayUtils.indexOf(5, BARRAY.length - 5, BARRAY, (byte) -5));
		assertEquals(5,
				ArrayUtils.indexOf(5, BARRAY.length - 5, BARRAY, (byte) 0));
		assertEquals(10,
				ArrayUtils.indexOf(5, BARRAY.length - 5, BARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.indexOf(10, 1, BARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.indexOf(10, 1, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(10, 1, BARRAY, (byte) 5));
	}

	/*
	 * ========================================================================
	 * byte[] array, byte[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfBaBa() {
		try {
			ArrayUtils.indexOf(null, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(BARRAY, BVALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(BARRAY, BVALUES_POS));
	}

	@Test
	public void testIndexOfIBaBa() {
		try {
			ArrayUtils.indexOf(0, null, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(0, BARRAY, BVALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(0, BARRAY, BVALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(5, BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(5, BARRAY, BVALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(6, BARRAY, BVALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(6, BARRAY, BVALUES_NEG));
		assertEquals(-1, ArrayUtils.indexOf(10, BARRAY, BVALUES_ZERO));
	}

	@Test
	public void testIndexOfIIBaBa() {
		try {
			ArrayUtils.indexOf(0, 1, null, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, 1, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, -1, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, BARRAY.length * 2, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOf(0, BARRAY.length, BARRAY, BVALUES_NEG));
		assertEquals(5,
				ArrayUtils.indexOf(0, BARRAY.length, BARRAY, BVALUES_ZERO));
		assertEquals(6,
				ArrayUtils.indexOf(0, BARRAY.length, BARRAY, BVALUES_POS));

		assertEquals(-1,
				ArrayUtils.indexOf(5, BARRAY.length - 5, BARRAY, BVALUES_NEG));
		assertEquals(5,
				ArrayUtils.indexOf(5, BARRAY.length - 5, BARRAY, BVALUES_ZERO));
		assertEquals(6,
				ArrayUtils.indexOf(5, BARRAY.length - 5, BARRAY, BVALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(6, 5, BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(5, 1, BARRAY, BVALUES_ZERO));
		assertEquals(-1, ArrayUtils.indexOf(2, 5, BARRAY, BVALUES_POS));
	}

	/*
	 * ========================================================================
	 * byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfAnyBaBa() {
		try {
			ArrayUtils.indexOfAny(null, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(BARRAY, BVALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(BARRAY, BVALUE_5));
	}

	@Test
	public void testIndexOfAnyIBaBa() {
		try {
			ArrayUtils.indexOfAny(0, null, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(-1, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(0, BARRAY, BVALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(0, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(0, BARRAY, BVALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(5, BARRAY, BVALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(5, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(5, BARRAY, BVALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(10, BARRAY, BVALUE_N5));
		assertEquals(-1, ArrayUtils.indexOfAny(10, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(10, BARRAY, BVALUE_5));
	}

	@Test
	public void testIndexOfAnyIIBaBa() {
		try {
			ArrayUtils.indexOfAny(0, 1, null, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(-1, 1, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(0, -1, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(0, BARRAY.length * 2, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOfAny(0, BARRAY.length, BARRAY, BVALUE_N5));
		assertEquals(5,
				ArrayUtils.indexOfAny(0, BARRAY.length, BARRAY, BVALUE_0));
		assertEquals(10,
				ArrayUtils.indexOfAny(0, BARRAY.length, BARRAY, BVALUE_5));

		assertEquals(-1,
				ArrayUtils.indexOfAny(5, BARRAY.length - 5, BARRAY, BVALUE_N5));
		assertEquals(5,
				ArrayUtils.indexOfAny(5, BARRAY.length - 5, BARRAY, BVALUE_0));
		assertEquals(10,
				ArrayUtils.indexOfAny(5, BARRAY.length - 5, BARRAY, BVALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(10, 1, BARRAY, BVALUE_N5));
		assertEquals(-1, ArrayUtils.indexOfAny(10, 1, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(10, 1, BARRAY, BVALUE_5));
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte value
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfBaB() {
		try {
			ArrayUtils.lastIndexOf(null, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(BARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.lastIndexOf(BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(BARRAY, (byte) 5));
	}

	@Test
	public void testLastIndexOfIBaB() {
		try {
			ArrayUtils.lastIndexOf(0, null, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, BARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.lastIndexOf(0, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(0, BARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, BARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.lastIndexOf(5, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(5, BARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(10, BARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(10, BARRAY, (byte) 5));
	}

	@Test
	public void testLastIndexOfIIBaB() {
		try {
			ArrayUtils.lastIndexOf(0, 1, null, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, 1, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, -1, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, BARRAY.length * 2, BARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOf(0, BARRAY.length, BARRAY, (byte) -5));
		assertEquals(5,
				ArrayUtils.lastIndexOf(0, BARRAY.length, BARRAY, (byte) 0));
		assertEquals(10,
				ArrayUtils.lastIndexOf(0, BARRAY.length, BARRAY, (byte) 5));

		assertEquals(-1,
				ArrayUtils.lastIndexOf(5, BARRAY.length - 5, BARRAY, (byte) -5));
		assertEquals(5,
				ArrayUtils.lastIndexOf(5, BARRAY.length - 5, BARRAY, (byte) 0));
		assertEquals(10,
				ArrayUtils.lastIndexOf(5, BARRAY.length - 5, BARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(10, 1, BARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, 1, BARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(10, 1, BARRAY, (byte) 5));
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfBaBa() {
		try {
			ArrayUtils.lastIndexOf(null, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(BARRAY, BVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(BARRAY, BVALUES_POS));
	}

	@Test
	public void testLastIndexOfIBaBa() {
		try {
			ArrayUtils.lastIndexOf(0, null, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(0, BARRAY, BVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(0, BARRAY, BVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, BARRAY, BVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(6, BARRAY, BVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(6, BARRAY, BVALUES_NEG));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, BARRAY, BVALUES_ZERO));
	}

	@Test
	public void testLastIndexOfIIBaBa() {
		try {
			ArrayUtils.lastIndexOf(0, 1, null, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, 1, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, -1, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, BARRAY.length * 2, BARRAY, BVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOf(0, BARRAY.length, BARRAY, BVALUES_NEG));
		assertEquals(5,
				ArrayUtils.lastIndexOf(0, BARRAY.length, BARRAY, BVALUES_ZERO));
		assertEquals(6,
				ArrayUtils.lastIndexOf(0, BARRAY.length, BARRAY, BVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, BARRAY.length - 5, BARRAY,
				BVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, BARRAY.length - 5, BARRAY,
				BVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(5, BARRAY.length - 5, BARRAY,
				BVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(6, 5, BARRAY, BVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, 1, BARRAY, BVALUES_ZERO));
		assertEquals(-1, ArrayUtils.lastIndexOf(2, 5, BARRAY, BVALUES_POS));
	}

	/*
	 * ========================================================================
	 * REVERSE byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfAnyBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(null, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(BARRAY, BVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(BARRAY, BVALUE_5));
	}

	@Test
	public void testLastIndexOfAnyIBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(0, null, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(-1, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(0, BARRAY, BVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(0, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(0, BARRAY, BVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(5, BARRAY, BVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(5, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(5, BARRAY, BVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, BARRAY, BVALUE_N5));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(10, BARRAY, BVALUE_5));
	}

	@Test
	public void testLastIndexOfAnyIIBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(0, 1, null, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(-1, 1, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(0, -1, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(0, BARRAY.length * 2, BARRAY, BVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOfAny(0, BARRAY.length, BARRAY, BVALUE_N5));
		assertEquals(5,
				ArrayUtils.lastIndexOfAny(0, BARRAY.length, BARRAY, BVALUE_0));
		assertEquals(10,
				ArrayUtils.lastIndexOfAny(0, BARRAY.length, BARRAY, BVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(5, BARRAY.length - 5,
				BARRAY, BVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(5, BARRAY.length - 5, BARRAY,
				BVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(5, BARRAY.length - 5,
				BARRAY, BVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, 1, BARRAY, BVALUE_N5));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, 1, BARRAY, BVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(10, 1, BARRAY, BVALUE_5));
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
	public void testEqualsCaCa() {
		assertFalse(ArrayUtils.equals((char[]) null, CARRAY));
		assertFalse(ArrayUtils.equals(CARRAY, (char[]) null));
		assertFalse(ArrayUtils.equals(CVALUES_POS, CARRAY));

		assertTrue(ArrayUtils.equals((char[]) null, (char[]) null));
		assertTrue(ArrayUtils.equals(CARRAY, CARRAY));

		char[] copy = new char[CARRAY.length];
		System.arraycopy(CARRAY, 0, copy, 0, CARRAY.length);

		assertTrue(ArrayUtils.equals(CARRAY, copy));
	}

	@Test
	public void testEqualsICaICaI() {
		assertFalse(ArrayUtils.equals(0, CARRAY, 0, null, CARRAY.length));
		assertFalse(ArrayUtils.equals(0, null, 0, CARRAY, CARRAY.length));

		assertTrue(ArrayUtils.equals(0, CARRAY, 0, CARRAY, CARRAY.length));
		assertTrue(ArrayUtils.equals(0, CARRAY, 0, CVALUES_NEG,
				CVALUES_NEG.length));
		assertTrue(ArrayUtils.equals(CVALUES_NEG.length + 1, CARRAY, 0,
				CVALUES_POS, CVALUES_POS.length));
	}

	@Test
	public void testEnsureCapacityICa() {
		assertEquals(CARRAY,
				ArrayUtils.ensureCapacity(CARRAY.length - 1, CARRAY));
		assertNotSame(CARRAY, ArrayUtils.ensureCapacity(CARRAY.length, CARRAY));

		char[] array = ArrayUtils.ensureCapacity(CARRAY.length, CARRAY);
		assertEquals(CARRAY.length, array.length);
		assertTrue(ArrayUtils.equals(0, array, 0, CARRAY, CARRAY.length));
	}

	@Test
	public void testEnsureCapacityIFCa() {
		assertEquals(CARRAY,
				ArrayUtils.ensureCapacity(CARRAY.length - 1, 2, CARRAY));
		assertNotSame(CARRAY,
				ArrayUtils.ensureCapacity(CARRAY.length, 2, CARRAY));

		char[] array = ArrayUtils.ensureCapacity(CARRAY.length, 2, CARRAY);
		assertEquals(CARRAY.length * 2, array.length);
		assertTrue(ArrayUtils.equals(0, array, 0, CARRAY, CARRAY.length));
	}

	/*
	 * ========================================================================
	 * char[] array, char value
	 * ========================================================================
	 */
	@Test
	public void testIndexOfCaC() {
		try {
			ArrayUtils.indexOf(null, (char) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(CARRAY, 'a'));
		assertEquals(5, ArrayUtils.indexOf(CARRAY, '0'));
		assertEquals(10, ArrayUtils.indexOf(CARRAY, '5'));
	}

	@Test
	public void testIndexOfICaC() {
		try {
			ArrayUtils.indexOf(0, null, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, CARRAY, 'a'));
		assertEquals(5, ArrayUtils.indexOf(0, CARRAY, '0'));
		assertEquals(10, ArrayUtils.indexOf(0, CARRAY, '5'));

		assertEquals(-1, ArrayUtils.indexOf(5, CARRAY, 'a'));
		assertEquals(5, ArrayUtils.indexOf(5, CARRAY, '0'));
		assertEquals(10, ArrayUtils.indexOf(5, CARRAY, '5'));

		assertEquals(-1, ArrayUtils.indexOf(10, CARRAY, 'a'));
		assertEquals(-1, ArrayUtils.indexOf(10, CARRAY, '0'));
		assertEquals(10, ArrayUtils.indexOf(10, CARRAY, '5'));
	}

	@Test
	public void testIndexOfIICaC() {
		try {
			ArrayUtils.indexOf(0, 1, null, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, 1, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, -1, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, CARRAY.length * 2, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, CARRAY.length, CARRAY, 'a'));
		assertEquals(5, ArrayUtils.indexOf(0, CARRAY.length, CARRAY, '0'));
		assertEquals(10, ArrayUtils.indexOf(0, CARRAY.length, CARRAY, '5'));

		assertEquals(-1, ArrayUtils.indexOf(5, CARRAY.length - 5, CARRAY, 'a'));
		assertEquals(5, ArrayUtils.indexOf(5, CARRAY.length - 5, CARRAY, '0'));
		assertEquals(10, ArrayUtils.indexOf(5, CARRAY.length - 5, CARRAY, '5'));

		assertEquals(-1, ArrayUtils.indexOf(10, 1, CARRAY, 'a'));
		assertEquals(-1, ArrayUtils.indexOf(10, 1, CARRAY, '0'));
		assertEquals(10, ArrayUtils.indexOf(10, 1, CARRAY, '5'));
	}

	/*
	 * ========================================================================
	 * char[] array, char[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfCaCa() {
		try {
			ArrayUtils.indexOf(null, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(CARRAY, CVALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(CARRAY, CVALUES_POS));
	}

	@Test
	public void testIndexOfICaCa() {
		try {
			ArrayUtils.indexOf(0, null, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(0, CARRAY, CVALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(0, CARRAY, CVALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(5, CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(5, CARRAY, CVALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(6, CARRAY, CVALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(6, CARRAY, CVALUES_NEG));
		assertEquals(-1, ArrayUtils.indexOf(10, CARRAY, CVALUES_ZERO));
	}

	@Test
	public void testIndexOfIICaCa() {
		try {
			ArrayUtils.indexOf(0, 1, null, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, 1, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, -1, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, CARRAY.length * 2, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOf(0, CARRAY.length, CARRAY, CVALUES_NEG));
		assertEquals(5,
				ArrayUtils.indexOf(0, CARRAY.length, CARRAY, CVALUES_ZERO));
		assertEquals(6,
				ArrayUtils.indexOf(0, CARRAY.length, CARRAY, CVALUES_POS));

		assertEquals(-1,
				ArrayUtils.indexOf(5, CARRAY.length - 5, CARRAY, CVALUES_NEG));
		assertEquals(5,
				ArrayUtils.indexOf(5, CARRAY.length - 5, CARRAY, CVALUES_ZERO));
		assertEquals(6,
				ArrayUtils.indexOf(5, CARRAY.length - 5, CARRAY, CVALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(6, 5, CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(5, 1, CARRAY, CVALUES_ZERO));
		assertEquals(-1, ArrayUtils.indexOf(2, 5, CARRAY, CVALUES_POS));
	}

	/*
	 * ========================================================================
	 * char[] array, ANY char[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfAnyCaCa() {
		try {
			ArrayUtils.indexOfAny(null, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(CARRAY, CVALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(CARRAY, CVALUE_5));
	}

	@Test
	public void testIndexOfAnyICaCa() {
		try {
			ArrayUtils.indexOfAny(0, null, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(-1, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(0, CARRAY, CVALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(0, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(0, CARRAY, CVALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(5, CARRAY, CVALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(5, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(5, CARRAY, CVALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(10, CARRAY, CVALUE_N5));
		assertEquals(-1, ArrayUtils.indexOfAny(10, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(10, CARRAY, CVALUE_5));
	}

	@Test
	public void testIndexOfAnyIICaCa() {
		try {
			ArrayUtils.indexOfAny(0, 1, null, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(-1, 1, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(0, -1, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(0, CARRAY.length * 2, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.indexOfAny(0, CARRAY.length, CARRAY, CVALUE_N5));
		assertEquals(5,
				ArrayUtils.indexOfAny(0, CARRAY.length, CARRAY, CVALUE_0));
		assertEquals(10,
				ArrayUtils.indexOfAny(0, CARRAY.length, CARRAY, CVALUE_5));

		assertEquals(-1,
				ArrayUtils.indexOfAny(5, CARRAY.length - 5, CARRAY, CVALUE_N5));
		assertEquals(5,
				ArrayUtils.indexOfAny(5, CARRAY.length - 5, CARRAY, CVALUE_0));
		assertEquals(10,
				ArrayUtils.indexOfAny(5, CARRAY.length - 5, CARRAY, CVALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(10, 1, CARRAY, CVALUE_N5));
		assertEquals(-1, ArrayUtils.indexOfAny(10, 1, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(10, 1, CARRAY, CVALUE_5));
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, char value
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfCaC() {
		try {
			ArrayUtils.lastIndexOf(null, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(CARRAY, 'a'));
		assertEquals(5, ArrayUtils.lastIndexOf(CARRAY, '0'));
		assertEquals(10, ArrayUtils.lastIndexOf(CARRAY, '5'));
	}

	@Test
	public void testLastIndexOfICaC() {
		try {
			ArrayUtils.lastIndexOf(0, null, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, CARRAY, 'a'));
		assertEquals(5, ArrayUtils.lastIndexOf(0, CARRAY, '0'));
		assertEquals(10, ArrayUtils.lastIndexOf(0, CARRAY, '5'));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, CARRAY, 'a'));
		assertEquals(5, ArrayUtils.lastIndexOf(5, CARRAY, '0'));
		assertEquals(10, ArrayUtils.lastIndexOf(5, CARRAY, '5'));

		assertEquals(-1, ArrayUtils.lastIndexOf(10, CARRAY, 'a'));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, CARRAY, '0'));
		assertEquals(10, ArrayUtils.lastIndexOf(10, CARRAY, '5'));
	}

	@Test
	public void testLastIndexOfIICaC() {
		try {
			ArrayUtils.lastIndexOf(0, 1, null, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, 1, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, -1, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, CARRAY.length * 2, CARRAY, '0');
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, CARRAY.length, CARRAY, 'a'));
		assertEquals(5, ArrayUtils.lastIndexOf(0, CARRAY.length, CARRAY, '0'));
		assertEquals(10, ArrayUtils.lastIndexOf(0, CARRAY.length, CARRAY, '5'));

		assertEquals(-1,
				ArrayUtils.lastIndexOf(5, CARRAY.length - 5, CARRAY, 'a'));
		assertEquals(5,
				ArrayUtils.lastIndexOf(5, CARRAY.length - 5, CARRAY, '0'));
		assertEquals(10,
				ArrayUtils.lastIndexOf(5, CARRAY.length - 5, CARRAY, '5'));

		assertEquals(-1, ArrayUtils.lastIndexOf(10, 1, CARRAY, 'a'));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, 1, CARRAY, '0'));
		assertEquals(10, ArrayUtils.lastIndexOf(10, 1, CARRAY, '5'));
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, char[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfCaCa() {
		try {
			ArrayUtils.lastIndexOf(null, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(CARRAY, CVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(CARRAY, CVALUES_POS));
	}

	@Test
	public void testLastIndexOfICaCa() {
		try {
			ArrayUtils.lastIndexOf(0, null, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(0, CARRAY, CVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(0, CARRAY, CVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, CARRAY, CVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(6, CARRAY, CVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(6, CARRAY, CVALUES_NEG));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, CARRAY, CVALUES_ZERO));
	}

	@Test
	public void testLastIndexOfIICaCa() {
		try {
			ArrayUtils.lastIndexOf(0, 1, null, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, 1, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, -1, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, CARRAY.length * 2, CARRAY, CVALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOf(0, CARRAY.length, CARRAY, CVALUES_NEG));
		assertEquals(5,
				ArrayUtils.lastIndexOf(0, CARRAY.length, CARRAY, CVALUES_ZERO));
		assertEquals(6,
				ArrayUtils.lastIndexOf(0, CARRAY.length, CARRAY, CVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, CARRAY.length - 5, CARRAY,
				CVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, CARRAY.length - 5, CARRAY,
				CVALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(5, CARRAY.length - 5, CARRAY,
				CVALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(6, 5, CARRAY, CVALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, 1, CARRAY, CVALUES_ZERO));
		assertEquals(-1, ArrayUtils.lastIndexOf(2, 5, CARRAY, CVALUES_POS));
	}

	/*
	 * ========================================================================
	 * REVERSE char[] array, ANY char[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfAnyCaCa() {
		try {
			ArrayUtils.lastIndexOfAny(null, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(CARRAY, CVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(CARRAY, CVALUE_5));
	}

	@Test
	public void testLastIndexOfAnyICaCa() {
		try {
			ArrayUtils.lastIndexOfAny(0, null, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(-1, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(0, CARRAY, CVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(0, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(0, CARRAY, CVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(5, CARRAY, CVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(5, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(5, CARRAY, CVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, CARRAY, CVALUE_N5));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(10, CARRAY, CVALUE_5));
	}

	@Test
	public void testLastIndexOfAnyIICaCa() {
		try {
			ArrayUtils.lastIndexOfAny(0, 1, null, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(-1, 1, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(0, -1, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(0, CARRAY.length * 2, CARRAY, CVALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0,
				ArrayUtils.lastIndexOfAny(0, CARRAY.length, CARRAY, CVALUE_N5));
		assertEquals(5,
				ArrayUtils.lastIndexOfAny(0, CARRAY.length, CARRAY, CVALUE_0));
		assertEquals(10,
				ArrayUtils.lastIndexOfAny(0, CARRAY.length, CARRAY, CVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(5, CARRAY.length - 5,
				CARRAY, CVALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(5, CARRAY.length - 5, CARRAY,
				CVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(5, CARRAY.length - 5,
				CARRAY, CVALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, 1, CARRAY, CVALUE_N5));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, 1, CARRAY, CVALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(10, 1, CARRAY, CVALUE_5));
	}
}