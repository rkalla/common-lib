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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <strong>NOTE</strong>: This isn't a totally fair benchmark comparison as
 * using Integer.parseInt requires that the incoming byte-stream be run through
 * a character-conversion process and then all the values turned into String
 * instances before the numbers can ever be parsed from it.
 * 
 * The memory and performance overhead of the NumberUtils class is an order of
 * magnitude less than the JDK's Integer.parseInt method.
 */
public class NumberUtilsBenchmark {
	public static final int ITERS = 20;

	public static int length = 0;
	public static final byte[] DATA = new byte[460000];
	public static final String[] DATA_STRINGS = new String[64 * 1024];

	static {
		InputStream is = NumberUtilsBenchmark.class
				.getResourceAsStream("65k-numbers.txt");

		try {
			length = is.read(DATA);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(
				NumberUtilsBenchmark.class
						.getResourceAsStream("65k-numbers.txt")));

		int i = 0;

		try {
			while (in.ready()) {
				DATA_STRINGS[i++] = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		jdkIntegerParseIntBenchmark();
		commonLibParseIntBenchmark();
	}

	public static void jdkIntegerParseIntBenchmark() {
		long time = System.currentTimeMillis();
		for (int loop = 0; loop < ITERS; loop++) {
			for (int i = 0; i < DATA_STRINGS.length; i++) {
				Integer.parseInt(DATA_STRINGS[i]);
			}
		}
		System.out.println("JDK Integer.parseInt \t\telapsed time: "
				+ (System.currentTimeMillis() - time) + "ms\t("
				+ (DATA_STRINGS.length * ITERS) + " numbers parsed, "
				+ (DATA_STRINGS.length * 5 * ITERS) + " bytes processed)");
	}

	public static void commonLibParseIntBenchmark() {
		long time = System.currentTimeMillis();
		for (int loop = 0; loop < ITERS; loop++) {
			for (int i = 0; i < length; i += 7) {
				NumberUtils.parseInt(i, 5, DATA);
			}
		}
		System.out.println("common-lib NumberUtils.parseInt elapsed time: "
				+ (System.currentTimeMillis() - time) + "ms\t("
				+ (DATA_STRINGS.length * ITERS) + " numbers parsed, "
				+ (length * ITERS) + " bytes processed)");
	}
}