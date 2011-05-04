package com.thebuzzmedia.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayUtilsTest {
	public static final byte[] ARRAY = { -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5 };

	public static final byte[] VALUE_N5 = { -5 };
	public static final byte[] VALUE_0 = { 0 };
	public static final byte[] VALUE_5 = { 5 };

	public static final byte[] VALUES_NEG = { -5, -4, -3, -2, -1 };
	public static final byte[] VALUES_ZERO = { 0 };
	public static final byte[] VALUES_POS = { 1, 2, 3, 4, 5 };

	/*
	 * ========================================================================
	 * byte[] array, byte value
	 * ========================================================================
	 */
	@Test
	public void testIndexOfBaB() {
		try {
			ArrayUtils.indexOf(null, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(ARRAY, (byte) 5));
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
			ArrayUtils.indexOf(-1, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(0, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(0, ARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.indexOf(5, ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(5, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(5, ARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.indexOf(10, ARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.indexOf(10, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(10, ARRAY, (byte) 5));
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
			ArrayUtils.indexOf(-1, 1, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, -1, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, ARRAY.length * 2, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, ARRAY.length, ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.indexOf(0, ARRAY.length, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(0, ARRAY.length, ARRAY, (byte) 5));

		assertEquals(-1,
				ArrayUtils.indexOf(5, ARRAY.length - 5, ARRAY, (byte) -5));
		assertEquals(5,
				ArrayUtils.indexOf(5, ARRAY.length - 5, ARRAY, (byte) 0));
		assertEquals(10,
				ArrayUtils.indexOf(5, ARRAY.length - 5, ARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.indexOf(10, 1, ARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.indexOf(10, 1, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.indexOf(10, 1, ARRAY, (byte) 5));
	}

	/*
	 * ========================================================================
	 * byte[] array, byte[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfBaBa() {
		try {
			ArrayUtils.indexOf(null, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(ARRAY, VALUES_POS));
	}

	@Test
	public void testIndexOfIBaBa() {
		try {
			ArrayUtils.indexOf(0, null, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(0, ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(0, ARRAY, VALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(5, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(5, ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(6, ARRAY, VALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(6, ARRAY, VALUES_NEG));
		assertEquals(-1, ArrayUtils.indexOf(10, ARRAY, VALUES_ZERO));
	}

	@Test
	public void testIndexOfIIBaBa() {
		try {
			ArrayUtils.indexOf(0, 1, null, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(-1, 1, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, -1, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOf(0, ARRAY.length * 2, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOf(0, ARRAY.length, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(0, ARRAY.length, ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.indexOf(0, ARRAY.length, ARRAY, VALUES_POS));

		assertEquals(-1,
				ArrayUtils.indexOf(5, ARRAY.length - 5, ARRAY, VALUES_NEG));
		assertEquals(5,
				ArrayUtils.indexOf(5, ARRAY.length - 5, ARRAY, VALUES_ZERO));
		assertEquals(6,
				ArrayUtils.indexOf(5, ARRAY.length - 5, ARRAY, VALUES_POS));

		assertEquals(-1, ArrayUtils.indexOf(6, 5, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.indexOf(5, 1, ARRAY, VALUES_ZERO));
		assertEquals(-1, ArrayUtils.indexOf(2, 5, ARRAY, VALUES_POS));
	}

	/*
	 * ========================================================================
	 * byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	@Test
	public void testIndexOfAnyBaBa() {
		try {
			ArrayUtils.indexOfAny(null, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(ARRAY, VALUE_5));
	}

	@Test
	public void testIndexOfAnyIBaBa() {
		try {
			ArrayUtils.indexOfAny(0, null, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(-1, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(0, ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(0, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(0, ARRAY, VALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(5, ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(5, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(5, ARRAY, VALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(10, ARRAY, VALUE_N5));
		assertEquals(-1, ArrayUtils.indexOfAny(10, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(10, ARRAY, VALUE_5));
	}

	@Test
	public void testIndexOfAnyIIBaBa() {
		try {
			ArrayUtils.indexOfAny(0, 1, null, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(-1, 1, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(0, -1, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.indexOfAny(0, ARRAY.length * 2, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.indexOfAny(0, ARRAY.length, ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.indexOfAny(0, ARRAY.length, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(0, ARRAY.length, ARRAY, VALUE_5));

		assertEquals(-1,
				ArrayUtils.indexOfAny(5, ARRAY.length - 5, ARRAY, VALUE_N5));
		assertEquals(5,
				ArrayUtils.indexOfAny(5, ARRAY.length - 5, ARRAY, VALUE_0));
		assertEquals(10,
				ArrayUtils.indexOfAny(5, ARRAY.length - 5, ARRAY, VALUE_5));

		assertEquals(-1, ArrayUtils.indexOfAny(10, 1, ARRAY, VALUE_N5));
		assertEquals(-1, ArrayUtils.indexOfAny(10, 1, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.indexOfAny(10, 1, ARRAY, VALUE_5));
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

		assertEquals(0, ArrayUtils.lastIndexOf(ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.lastIndexOf(ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(ARRAY, (byte) 5));
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
			ArrayUtils.lastIndexOf(-1, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.lastIndexOf(0, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(0, ARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.lastIndexOf(5, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(5, ARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(10, ARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(10, ARRAY, (byte) 5));
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
			ArrayUtils.lastIndexOf(-1, 1, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, -1, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, ARRAY.length * 2, ARRAY, (byte) 0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, ARRAY.length, ARRAY, (byte) -5));
		assertEquals(5, ArrayUtils.lastIndexOf(0, ARRAY.length, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(0, ARRAY.length, ARRAY, (byte) 5));

		assertEquals(-1,
				ArrayUtils.lastIndexOf(5, ARRAY.length - 5, ARRAY, (byte) -5));
		assertEquals(5,
				ArrayUtils.lastIndexOf(5, ARRAY.length - 5, ARRAY, (byte) 0));
		assertEquals(10,
				ArrayUtils.lastIndexOf(5, ARRAY.length - 5, ARRAY, (byte) 5));

		assertEquals(-1, ArrayUtils.lastIndexOf(10, 1, ARRAY, (byte) -5));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, 1, ARRAY, (byte) 0));
		assertEquals(10, ArrayUtils.lastIndexOf(10, 1, ARRAY, (byte) 5));
	}
	
	/*
	 * ========================================================================
	 * REVERSE byte[] array, byte[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfBaBa() {
		try {
			ArrayUtils.lastIndexOf(null, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(ARRAY, VALUES_POS));
	}

	@Test
	public void testLastIndexOfIBaBa() {
		try {
			ArrayUtils.lastIndexOf(0, null, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(0, ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(0, ARRAY, VALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(5, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(6, ARRAY, VALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(6, ARRAY, VALUES_NEG));
		assertEquals(-1, ArrayUtils.lastIndexOf(10, ARRAY, VALUES_ZERO));
	}

	@Test
	public void testLastIndexOfIIBaBa() {
		try {
			ArrayUtils.lastIndexOf(0, 1, null, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(-1, 1, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, -1, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOf(0, ARRAY.length * 2, ARRAY, VALUES_ZERO);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOf(0, ARRAY.length, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(0, ARRAY.length, ARRAY, VALUES_ZERO));
		assertEquals(6, ArrayUtils.lastIndexOf(0, ARRAY.length, ARRAY, VALUES_POS));

		assertEquals(-1,
				ArrayUtils.lastIndexOf(5, ARRAY.length - 5, ARRAY, VALUES_NEG));
		assertEquals(5,
				ArrayUtils.lastIndexOf(5, ARRAY.length - 5, ARRAY, VALUES_ZERO));
		assertEquals(6,
				ArrayUtils.lastIndexOf(5, ARRAY.length - 5, ARRAY, VALUES_POS));

		assertEquals(-1, ArrayUtils.lastIndexOf(6, 5, ARRAY, VALUES_NEG));
		assertEquals(5, ArrayUtils.lastIndexOf(5, 1, ARRAY, VALUES_ZERO));
		assertEquals(-1, ArrayUtils.lastIndexOf(2, 5, ARRAY, VALUES_POS));
	}
	
	/*
	 * ========================================================================
	 * REVERSE byte[] array, ANY byte[] values
	 * ========================================================================
	 */
	@Test
	public void testLastIndexOfAnyBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(null, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(ARRAY, VALUE_5));
	}

	@Test
	public void testLastIndexOfAnyIBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(0, null, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(-1, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(0, ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(0, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(0, ARRAY, VALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(5, ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(5, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(5, ARRAY, VALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, ARRAY, VALUE_N5));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(10, ARRAY, VALUE_5));
	}

	@Test
	public void testLastIndexOfAnyIIBaBa() {
		try {
			ArrayUtils.lastIndexOfAny(0, 1, null, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(-1, 1, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(0, -1, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			ArrayUtils.lastIndexOfAny(0, ARRAY.length * 2, ARRAY, VALUE_0);
			assertTrue(false); // shouldn't get here
		} catch (Exception e) {
			assertTrue(true);
		}

		assertEquals(0, ArrayUtils.lastIndexOfAny(0, ARRAY.length, ARRAY, VALUE_N5));
		assertEquals(5, ArrayUtils.lastIndexOfAny(0, ARRAY.length, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(0, ARRAY.length, ARRAY, VALUE_5));

		assertEquals(-1,
				ArrayUtils.lastIndexOfAny(5, ARRAY.length - 5, ARRAY, VALUE_N5));
		assertEquals(5,
				ArrayUtils.lastIndexOfAny(5, ARRAY.length - 5, ARRAY, VALUE_0));
		assertEquals(10,
				ArrayUtils.lastIndexOfAny(5, ARRAY.length - 5, ARRAY, VALUE_5));

		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, 1, ARRAY, VALUE_N5));
		assertEquals(-1, ArrayUtils.lastIndexOfAny(10, 1, ARRAY, VALUE_0));
		assertEquals(10, ArrayUtils.lastIndexOfAny(10, 1, ARRAY, VALUE_5));
	}
}