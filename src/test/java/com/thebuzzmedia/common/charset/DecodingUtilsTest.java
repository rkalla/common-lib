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
package com.thebuzzmedia.common.charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.junit.Test;

public class DecodingUtilsTest {
	public static int asciiLength = 0;
	public static int utf8Length = 0;
	public static byte[] ASCII_DATA = new byte[512];
	public static byte[] UTF8_DATA = new byte[1024];

	public static int asciiChecksumLength = 0;
	public static int utf8ChecksumLength = 0;
	public static char[] ASCII_CHECKSUM = new char[512];
	public static char[] UTF8_CHECKSUM = new char[1024];

	static {
		try {
			// Load raw data
			InputStream is = DecodingUtilsTest.class
					.getResourceAsStream("ascii.txt");
			asciiLength = is.read(ASCII_DATA);
			is.close();

			is = DecodingUtilsTest.class.getResourceAsStream("utf8.txt");
			utf8Length = is.read(UTF8_DATA);
			is.close();

			// Load the checksums using all JDK methods to ensure correctness
			InputStreamReader sr = new InputStreamReader(
					DecodingUtilsTest.class.getResourceAsStream("ascii.txt"),
					Charset.forName("ASCII"));
			asciiChecksumLength = sr.read(ASCII_CHECKSUM);
			sr.close();

			sr = new InputStreamReader(
					DecodingUtilsTest.class.getResourceAsStream("utf8.txt"),
					Charset.forName("UTF-8"));
			utf8ChecksumLength = sr.read(UTF8_CHECKSUM);
			sr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSmallMaxBuffer() {
		System.setProperty(DecodingUtils.MAX_BUFFER_SIZE_PROPERTY_NAME, "32");

		assertEquals(32,
				Integer.getInteger(DecodingUtils.MAX_BUFFER_SIZE_PROPERTY_NAME)
						.intValue());

		testDecodeBa();
	}

	@Test
	public void testDecodeBa() {
		char[] ascii = DecodingUtils.decode(ASCII_DATA);

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		char[] utf8 = DecodingUtils.decode(UTF8_DATA);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testDecodeBaCs() {
		char[] ascii = DecodingUtils.decode(ASCII_DATA,
				Charset.forName("ASCII"));

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		char[] utf8 = DecodingUtils.decode(UTF8_DATA, Charset.forName("UTF8"));

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testDecodeIIBa() {
		char[] ascii = DecodingUtils.decode(ASCII_DATA, 0, asciiLength);

		assertEquals(asciiChecksumLength, ascii.length);

		for (int i = 0; i < ascii.length; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		char[] utf8 = DecodingUtils.decode(UTF8_DATA, 0, utf8Length);

		// At most no longer than original bytes read.
		assertTrue(utf8.length <= utf8Length);
		assertEquals(utf8ChecksumLength, utf8.length);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testDecodeIIBaCs() {
		char[] ascii = DecodingUtils.decode(ASCII_DATA,
				Charset.forName("ASCII"), 0, asciiLength);

		assertEquals(asciiChecksumLength, ascii.length);

		for (int i = 0; i < ascii.length; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		char[] utf8 = DecodingUtils.decode(UTF8_DATA, Charset.forName("UTF-8"),
				0, utf8Length);

		// At most no longer than original bytes read.
		assertTrue(utf8.length <= utf8Length);
		assertEquals(utf8ChecksumLength, utf8.length);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testDecodeBb() {
		ByteBuffer bb = ByteBuffer.wrap(ASCII_DATA, 0, asciiLength);
		char[] ascii = DecodingUtils.decode(bb);

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		bb = ByteBuffer.wrap(UTF8_DATA, 0, utf8Length);
		char[] utf8 = DecodingUtils.decode(bb);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testDecodeBbCs() {
		ByteBuffer bb = ByteBuffer.wrap(ASCII_DATA, 0, asciiLength);
		char[] ascii = DecodingUtils.decode(bb, Charset.forName("ASCII"));

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		bb = ByteBuffer.wrap(UTF8_DATA, 0, utf8Length);
		char[] utf8 = DecodingUtils.decode(bb, Charset.forName("UTF-8"));

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}
}