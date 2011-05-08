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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;

public class DecodingUtils {
	public static final String MAX_BUFFER_SIZE_PROPERTY_NAME = "tbm.common.util.decode.maxBufferSize";

	public static final int MAX_BUFFER_SIZE = Integer.getInteger(
			MAX_BUFFER_SIZE_PROPERTY_NAME, 32768);

	public static final Charset ASCII_CHARSET = Charset.forName("ASCII");
	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	protected static Map<Charset, CharsetDecoder> decoderMap;

	static {
		decoderMap = new HashMap<Charset, CharsetDecoder>();

		decoderMap.put(ASCII_CHARSET, ASCII_CHARSET.newDecoder());
		decoderMap.put(UTF8_CHARSET, UTF8_CHARSET.newDecoder());
	}

	public static char[] decode(byte[] array) {
		return (array == null ? null : decode(array, UTF8_CHARSET, 0,
				array.length));
	}

	public static char[] decode(byte[] array, Charset charset) {
		return (array == null ? null : decode(array, charset, 0, array.length));
	}

	public static char[] decode(byte[] array, int index, int length) {
		return (array == null ? null : decode(
				ByteBuffer.wrap(array, index, length), UTF8_CHARSET));
	}

	public static char[] decode(byte[] array, Charset charset, int index,
			int length) {
		return (array == null ? null : decode(
				ByteBuffer.wrap(array, index, length), charset));
	}

	public static char[] decode(ByteBuffer source)
			throws IllegalArgumentException {
		return decode(source, UTF8_CHARSET);
	}

	public static char[] decode(ByteBuffer buffer, Charset charset)
			throws IllegalArgumentException {
		char[] result = null;

		if (buffer != null) {
			// Get the decoder for this charset, creating it if necessary.
			CharsetDecoder decoder = getDecoder(charset);

			/*
			 * In order to conserve memory, we create a target buffer size that
			 * is at most no larger than MAX_BUFFER_SIZE, but preferably smaller
			 * and just big enough to hold our decoded bytes.
			 * 
			 * During byte>char conversion, the most chars you could ever get
			 * out of decoding a byte is a single char (1:1), but in UTF-8 and
			 * 16 scenarios you may need to decode multiple bytes to get a
			 * single char. Because of this, simple using the ByteBuffer's
			 * remaining byte count as our target size is a good upper bound for
			 * our target buffer.
			 */
			int optimalSize = (buffer.remaining() < MAX_BUFFER_SIZE ? buffer
					.remaining() : MAX_BUFFER_SIZE);
			CharBuffer decodeBuffer = CharBuffer.allocate(optimalSize);

			/*
			 * We size our resultant char[] to match the optimal size we
			 * computed above. When working with ASCII or other charsets that
			 * have a 1-byte representation, these sizes will be exactly right
			 * and not need resizing.
			 * 
			 * When working with higher-order unicode chars, our final array
			 * will need to be trimmed because the number of chars in it is less
			 * than the number of bytes.
			 * 
			 * In the case where our optimal size wasn't big enough, the
			 * resulting array will be grown as big as necessary to contain the
			 * final decoded result regardless of what MAX_BUFFER_SIZE is.
			 */
			result = new char[optimalSize];
			int resultLength = 0;

			// Reset the decoder
			decoder.reset();

			while (buffer.hasRemaining()) {
				// Reset the decode buffer
				decodeBuffer.clear();

				/*
				 * Decode the first buffer.capacity chars, passing 'false' to
				 * indicate that we aren't sure if we are done with the decode
				 * operation yet.
				 */
				decoder.decode(buffer, decodeBuffer, false);

				// Prepare buffer to be read from.
				decodeBuffer.flip();

				int appendLength = decodeBuffer.remaining();

				// Append what we successfully decoded to our tally
				result = append(decodeBuffer, resultLength, result);

				// Update our result length
				resultLength += appendLength;

				// If there is no more to decode, go through finalization
				if (!buffer.hasRemaining()) {
					decodeBuffer.clear();

					/*
					 * Per the CharsetDecoder Javadocs, decoders must be given
					 * an opportunity to "finalize" their internal state and
					 * flush out any pending operations once we know we've hit
					 * the end of the chars to decode.
					 */
					decoder.decode(buffer, decodeBuffer, true);
					decoder.flush(decodeBuffer);

					decodeBuffer.flip();
					appendLength = decodeBuffer.remaining();

					// If any finalized bytes were written, append them.
					if (decodeBuffer.hasRemaining()) {
						result = append(decodeBuffer, resultLength, result);

						// Update our result length
						resultLength += appendLength;
					}
				}
			}

			// Last-check to make sure our result is exactly the right size
			if (result.length != resultLength) {
				char[] newArray = new char[resultLength];
				System.arraycopy(result, 0, newArray, 0, resultLength);
				result = newArray;
			}
		}

		return result;
	}

	protected static synchronized CharsetDecoder getDecoder(Charset charset)
			throws IllegalArgumentException {
		if (charset == null)
			throw new IllegalArgumentException(
					"charset cannot be null, consider using one of the pre-defined Charset constants from this class for convenience.");

		CharsetDecoder result = decoderMap.get(charset);

		if (result == null) {
			result = charset.newDecoder();
			decoderMap.put(charset, result);
		}

		return result;
	}

	protected static char[] append(CharBuffer source, int index, char[] dest) {
		// Do nothing if there is nothing to append.
		if (source == null || source.remaining() == 0)
			return dest;

		int byteCount = source.remaining();
		int requiredCapacity = index + byteCount;

		// Make sure our dest array is large enough; resize as necessary.
		if (requiredCapacity > dest.length) {
			char[] newArray = new char[requiredCapacity];
			System.arraycopy(dest, 0, newArray, 0, dest.length);
			dest = newArray;
		}

		// Ask the buffer to copy the chars directly into our target array.
		source.get(dest, index, byteCount);

		// Return the modified array to the caller.
		return dest;
	}
}