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
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

public class EncodingUtils {
	public static final String MAX_BUFFER_SIZE_PROPERTY_NAME = "tbm.common.encode.maxBufferSize";

	public static final int MAX_BUFFER_SIZE = Integer.getInteger(
			MAX_BUFFER_SIZE_PROPERTY_NAME, 32768);

	public static final Charset ASCII_CHARSET = Charset.forName("ASCII");
	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	protected static Map<Charset, CharsetEncoder> encoderMap;

	static {
		encoderMap = new HashMap<Charset, CharsetEncoder>();

		encoderMap.put(ASCII_CHARSET, ASCII_CHARSET.newEncoder());
		encoderMap.put(UTF8_CHARSET, UTF8_CHARSET.newEncoder());
	}

	public static byte[] encode(char[] array) {
		return (array == null ? null : encode(array, UTF8_CHARSET, 0,
				array.length));
	}

	public static byte[] encode(char[] array, Charset charset) {
		return (array == null ? null : encode(array, charset, 0, array.length));
	}

	public static byte[] encode(char[] array, int index, int length) {
		return (array == null ? null : encode(
				CharBuffer.wrap(array, index, length), UTF8_CHARSET));
	}

	public static byte[] encode(char[] array, Charset charset, int index,
			int length) {
		return (array == null ? null : encode(
				CharBuffer.wrap(array, index, length), charset));
	}

	public static byte[] encode(CharSequence chars) {
		return (chars == null ? null : encode(CharBuffer.wrap(chars)));
	}

	public static byte[] encode(CharSequence chars, Charset charset) {
		return (chars == null ? null : encode(CharBuffer.wrap(chars), charset));
	}

	public static byte[] encode(CharBuffer source)
			throws IllegalArgumentException {
		return encode(source, UTF8_CHARSET);
	}

	public static byte[] encode(CharBuffer buffer, Charset charset)
			throws IllegalArgumentException {
		byte[] result = null;

		if (buffer != null) {
			// Get the encoder for this charset, creating it if necessary.
			CharsetEncoder encoder = getEncoder(charset);
			int optimalSize = Math.round(encoder.averageBytesPerChar()
					* (float) buffer.remaining());

			/*
			 * In order to conserve memory, we create a target buffer size that
			 * is at most no larger than MAX_BUFFER_SIZE, but preferably smaller
			 * and just big enough to hold our encoded chars.
			 * 
			 * During char>byte conversion, you always have at least 1 byte for
			 * 1 char (1:1) but with higher order unicode chars you can
			 * frequently get multiple bytes per char, so we need to guestimate
			 * the optimal size based off of hints from our encoder.
			 */
			ByteBuffer encodeBuffer = ByteBuffer
					.allocate((optimalSize < MAX_BUFFER_SIZE ? optimalSize
							: MAX_BUFFER_SIZE));

			/*
			 * We size our resultant byte[] to match the optimal size we
			 * computed above. When working with ASCII or other charsets that
			 * have a 1-byte representation, these sizes will be exactly right
			 * and not need resizing.
			 * 
			 * When working with higher-order unicode chars, our array may need
			 * to be grown dynamically while encoding.
			 */
			result = new byte[optimalSize];
			int resultLength = 0;

			// Reset the encoder
			encoder.reset();

			while (buffer.hasRemaining()) {
				// Reset the encode buffer
				encodeBuffer.clear();

				/*
				 * Encode the first buffer.capacity chars, passing 'false' to
				 * indicate that we aren't sure if we are done with the encode
				 * operation yet.
				 */
				encoder.encode(buffer, encodeBuffer, false);

				// Prepare buffer to be read from.
				encodeBuffer.flip();

				int appendLength = encodeBuffer.remaining();

				// Append what we successfully encoded to our tally
				result = append(encodeBuffer, resultLength, result);

				// Update our result length
				resultLength += appendLength;

				// If there is no more to decode, go through finalization
				if (!buffer.hasRemaining()) {
					encodeBuffer.clear();

					/*
					 * Per the CharsetDecoder Javadocs, decoders must be given
					 * an opportunity to "finalize" their internal state and
					 * flush out any pending operations once we know we've hit
					 * the end of the chars to decode.
					 */
					encoder.encode(buffer, encodeBuffer, true);
					encoder.flush(encodeBuffer);

					encodeBuffer.flip();
					appendLength = encodeBuffer.remaining();

					// If any finalized bytes were written, append them.
					if (encodeBuffer.hasRemaining()) {
						result = append(encodeBuffer, resultLength, result);

						// Update our result length
						resultLength += appendLength;
					}
				}
			}

			// Last-check to make sure our result is exactly the right size
			if (result.length != resultLength) {
				byte[] newArray = new byte[resultLength];
				System.arraycopy(result, 0, newArray, 0, resultLength);
				result = newArray;
			}
		}

		return result;
	}

	protected static synchronized CharsetEncoder getEncoder(Charset charset)
			throws IllegalArgumentException {
		if (charset == null)
			throw new IllegalArgumentException(
					"charset cannot be null, consider using one of the pre-defined Charset constants from this class for convenience.");

		CharsetEncoder result = encoderMap.get(charset);

		if (result == null) {
			result = charset.newEncoder();
			encoderMap.put(charset, result);
		}

		return result;
	}

	protected static byte[] append(ByteBuffer source, int index, byte[] dest) {
		// Do nothing if there is nothing to append.
		if (source == null || source.remaining() == 0)
			return dest;

		int byteCount = source.remaining();
		int requiredCapacity = index + byteCount;

		// Make sure our dest array is large enough; resize as necessary.
		if (requiredCapacity > dest.length) {
			byte[] newArray = new byte[requiredCapacity];
			System.arraycopy(dest, 0, newArray, 0, dest.length);
			dest = newArray;
		}

		// Ask the buffer to copy the chars directly into our target array.
		source.get(dest, index, byteCount);

		// Return the modified array to the caller.
		return dest;
	}
}