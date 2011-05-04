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
import java.text.Format;
import java.text.NumberFormat;

public class ArrayUtilsBenchmark {
	public static final int ITERS = 100;

	public static final byte VAL = (byte) '#';
	public static final byte[] VALS = { (byte) '[', (byte) '#', (byte) ']' };

	public static final byte[] DATA = new byte[1024 * 65];

	private static final Format FORMAT = NumberFormat.getInstance();

	static {
		InputStream is = ArrayUtilsBenchmark.class
				.getResourceAsStream("65k.txt");

		try {
			is.read(DATA);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		indexOfValBenchmark();
		indexOfValsBenchmark();
		indexOfAnyValsBenchmark();
	}

	public static void indexOfValBenchmark() {
		long time = System.currentTimeMillis();
		for (int i = 0; i < ITERS; i++) {
			if (ArrayUtils.indexOf(DATA, VAL) == -1)
				throw new RuntimeException(
						"indexOf returned -1, it shouldn't ever do this in this benchmark.");
		}
		System.out.println("indexOf byte\t\telapsed time: "
				+ (System.currentTimeMillis() - time) + "ms\t("
				+ FORMAT.format((DATA.length / 2) * ITERS) + " bytes scanned)");
	}

	public static void indexOfValsBenchmark() {
		long time = System.currentTimeMillis();
		for (int i = 0; i < ITERS; i++) {
			if (ArrayUtils.indexOf(DATA, VALS) == -1)
				throw new RuntimeException(
						"indexOf returned -1, it shouldn't ever do this in this benchmark.");
		}
		System.out.println("indexOf byte[]\t\telapsed time: "
				+ (System.currentTimeMillis() - time) + "ms\t("
				+ FORMAT.format((DATA.length / 2) * ITERS) + " bytes scanned)");
	}

	public static void indexOfAnyValsBenchmark() {
		long time = System.currentTimeMillis();
		for (int i = 0; i < ITERS; i++) {
			if (ArrayUtils.indexOfAny(DATA, VALS) == -1)
				throw new RuntimeException(
						"indexOf returned -1, it shouldn't ever do this in this benchmark.");
		}
		System.out.println("indexOfAny byte[]\telapsed time: "
				+ (System.currentTimeMillis() - time) + "ms\t("
				+ FORMAT.format((DATA.length / 2) * ITERS) + " bytes scanned)");
	}
}