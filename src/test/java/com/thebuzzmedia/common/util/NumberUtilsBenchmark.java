package com.thebuzzmedia.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		System.out.println("common-lib Integer.parseInt \telapsed time: "
				+ (System.currentTimeMillis() - time) + "ms\t("
				+ (DATA_STRINGS.length * ITERS) + " numbers parsed, "
				+ (length * ITERS) + " bytes processed)");
	}
}