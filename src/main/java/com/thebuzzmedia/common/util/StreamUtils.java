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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.thebuzzmedia.common.charset.DecodingUtils;

/**
 * @author Riyad Kalla (software@thebuzzmedia.com)
 * @since 2.3
 */
public class StreamUtils {
	public static final String MAX_BUFFER_SIZE_PROPERTY_NAME = "tbm.common.stream.maxBufferSize";

	public static final int MAX_BUFFER_SIZE = Integer.getInteger(
			MAX_BUFFER_SIZE_PROPERTY_NAME, 32768);

	public static char[] toChars(InputStream stream)
			throws IllegalArgumentException, IOException {
		return toChars(stream, Charset.forName("UTF-8"));
	}

	public static char[] toChars(InputStream stream, Charset charset)
			throws IllegalArgumentException, IOException {
		if (stream == null)
			throw new IllegalArgumentException("stream cannot be null");

		int bytesRead = 0;
		byte[] buffer = new byte[stream.available() < MAX_BUFFER_SIZE ? stream
				.available() : MAX_BUFFER_SIZE];
		char[] output = new char[0];

		// Read all the data from the stream.
		while ((bytesRead = stream.read(buffer)) > -1) {
			// Decode the read segment.
			char[] segment = DecodingUtils
					.decode(buffer, charset, 0, bytesRead);

			// Append the segment to the output.
			output = ArrayUtils.append(segment, output);
		}

		try {
			// Close the stream.
			stream.close();
		} catch (Exception e) {
			// no-op
		}

		return output;
	}
}