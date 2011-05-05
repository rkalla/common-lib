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
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.junit.Test;

public class EncodingUtilsTest {
	public static int asciiLength = 0;
	public static int utf8Length = 0;
	public static char[] ASCII_DATA = new char[512];
	public static char[] UTF8_DATA = new char[1024];

	public static int asciiChecksumLength = 0;
	public static int utf8ChecksumLength = 0;
	public static byte[] ASCII_CHECKSUM = new byte[512];
	public static byte[] UTF8_CHECKSUM = new byte[1024];

	static {
		try {
			// Load raw data
			InputStreamReader sr = new InputStreamReader(
					EncodingUtilsTest.class.getResourceAsStream("ascii.txt"),
					Charset.forName("ASCII"));
			asciiLength = sr.read(ASCII_DATA);
			sr.close();

			sr = new InputStreamReader(
					EncodingUtilsTest.class.getResourceAsStream("utf8.txt"),
					Charset.forName("UTF-8"));
			utf8Length = sr.read(UTF8_DATA);
			sr.close();

			// Load the checksums using all JDK methods to ensure correctness
			InputStream is = EncodingUtilsTest.class
					.getResourceAsStream("ascii.txt");
			asciiChecksumLength = is.read(ASCII_CHECKSUM);
			is.close();

			is = EncodingUtilsTest.class.getResourceAsStream("utf8.txt");
			utf8ChecksumLength = is.read(UTF8_CHECKSUM);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSmallMaxBuffer() {
		System.setProperty(EncodingUtils.MAX_BUFFER_SIZE_PROPERTY_NAME, "32");

		assertEquals(32,
				Integer.getInteger(EncodingUtils.MAX_BUFFER_SIZE_PROPERTY_NAME)
						.intValue());

		testEncodeBa();
	}

	@Test
	public void testEncodeBa() {
		byte[] ascii = EncodingUtils.encode(ASCII_DATA);

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		byte[] utf8 = EncodingUtils.encode(UTF8_DATA);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testEncodeBaCs() {
		byte[] ascii = EncodingUtils.encode(ASCII_DATA,
				Charset.forName("ASCII"));

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		byte[] utf8 = EncodingUtils.encode(UTF8_DATA, Charset.forName("UTF8"));

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testEncodeIIBa() {
		byte[] ascii = EncodingUtils.encode(0, asciiLength, ASCII_DATA);

		assertEquals(asciiChecksumLength, ascii.length);

		for (int i = 0; i < ascii.length; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		byte[] utf8 = EncodingUtils.encode(0, utf8Length, UTF8_DATA);

		// At least as long as original chars.
		assertTrue(utf8.length >= utf8Length);
		assertEquals(utf8ChecksumLength, utf8.length);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testEncodeIIBaCs() {
		byte[] ascii = EncodingUtils.encode(0, asciiLength, ASCII_DATA,
				Charset.forName("ASCII"));

		assertEquals(asciiChecksumLength, ascii.length);

		for (int i = 0; i < ascii.length; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		byte[] utf8 = EncodingUtils.encode(0, utf8Length, UTF8_DATA,
				Charset.forName("UTF-8"));

		// At least as long as original chars.
		assertTrue(utf8.length >= utf8Length);
		assertEquals(utf8ChecksumLength, utf8.length);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testEncodeBb() {
		CharBuffer cb = CharBuffer.wrap(ASCII_DATA, 0, asciiLength);
		byte[] ascii = EncodingUtils.encode(cb);

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		cb = CharBuffer.wrap(UTF8_DATA, 0, utf8Length);
		byte[] utf8 = EncodingUtils.encode(cb);

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}

	@Test
	public void testEncodeBbCs() {
		CharBuffer cb = CharBuffer.wrap(ASCII_DATA, 0, asciiLength);
		byte[] ascii = EncodingUtils.encode(cb, Charset.forName("ASCII"));

		assertEquals(asciiChecksumLength, asciiLength);

		for (int i = 0; i < asciiChecksumLength; i++)
			assertEquals(ASCII_CHECKSUM[i], ascii[i]);

		cb = CharBuffer.wrap(UTF8_DATA, 0, utf8Length);
		byte[] utf8 = EncodingUtils.encode(cb, Charset.forName("UTF-8"));

		for (int i = 0; i < utf8ChecksumLength; i++)
			assertEquals(UTF8_CHECKSUM[i], utf8[i]);
	}
}