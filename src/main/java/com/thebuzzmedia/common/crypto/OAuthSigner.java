package com.thebuzzmedia.common.crypto;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.thebuzzmedia.common.util.Base64;

public class OAuthSigner {
	/**
	 * All <code>String[]</code> data structures referenced by this class are
	 * meant to be of length 2, with 'name' in the 0th index position and
	 * 'value' in the 1st index position.
	 * <p/>
	 * This is a constant used to define the name's index, <code>0</code>.
	 */
	public static final int NAME_INDEX = 0;

	/**
	 * All <code>String[]</code> data structures referenced by this class are
	 * meant to be of length 2, with 'name' in the 0th index position and
	 * 'value' in the 1st index position.
	 * <p/>
	 * This is a constant used to define the value's index, <code>1</code>.
	 */
	public static final int VALUE_INDEX = 1;

	private static final ParameterComparator COMPARATOR = new ParameterComparator();

	/**
	 * Used to define the different strengths of hashing algorithms this class
	 * supports being used to hash the resultant signature base string.
	 */
	public enum Algorithm {
		MD5("HmacMD5"), SHA1("HmacSHA1"), SHA256("HmacSHA256"), SHA512(
				"HmacSHA512");

		/**
		 * Defined to match the "algorithm" argument understood by the
		 * {@link Mac} class in the JDK.
		 */
		private String name;

		private Algorithm(String name) {
			this.name = name;
		}
	}

	private String requestMethod;
	private String baseURI;
	private List<String[]> paramList;

	public OAuthSigner() {
		// default constructor
	}

	public OAuthSigner(String requestMethod, String baseURI,
			Map<String, String[]> paramMap, ParamFilter filter)
			throws IllegalArgumentException {
		requestMethod(requestMethod).baseURI(baseURI).params(paramMap, filter);
	}

	public OAuthSigner(String requestMethod, String baseURI,
			List<String[]> paramList, ParamFilter filter)
			throws IllegalArgumentException {
		requestMethod(requestMethod).baseURI(baseURI).params(paramList, filter);
	}

	public OAuthSigner requestMethod(String requestMethod)
			throws IllegalArgumentException {
		if (requestMethod == null || requestMethod.length() == 0)
			throw new IllegalArgumentException(
					"requestMethod cannot be null or empty");

		this.requestMethod = requestMethod;
		return this;
	}

	public void reset() {
		requestMethod = null;
		baseURI = null;

		if (paramList != null) {
			paramList.clear();
			paramList = null;
		}
	}

	public OAuthSigner baseURI(String baseURI) throws IllegalArgumentException {
		if (baseURI == null || baseURI.length() == 0)
			throw new IllegalArgumentException(
					"baseURI cannot be null or empty");

		this.baseURI = baseURI;
		return this;
	}

	public OAuthSigner params(Map<String, String[]> paramMap, ParamFilter filter) {
		if (paramMap == null || paramMap.isEmpty())
			return this;

		/*
		 * We use an array to store the interim list of name/value pairings and
		 * not a static array the same size of the map, because it is possible
		 * that the map contains a single name with multiple values that will be
		 * flattened out into n+1 values in the resulting paramList.
		 */
		this.paramList = new ArrayList<String[]>(paramMap.size());
		Iterator<Entry<String, String[]>> entries = paramMap.entrySet()
				.iterator();

		// Iterate over all the parameters
		while (entries.hasNext()) {
			Entry<String, String[]> entry = entries.next();
			String[] values = entry.getValue();

			// Skip right over empty-valued params.
			if (values == null || values.length == 0)
				continue;

			String name = entry.getKey();

			// Process each value as a separate param.
			for (int i = 0; i < values.length; i++) {
				String value = values[i];

				// Skip empty values
				if (value == null || value.length() == 0)
					continue;

				// Filter name/value pair if filter provided.
				if (filter != null && !filter.include(name, value))
					continue;

				// Add the name/value pair to our list.
				paramList.add(new String[] { name, value });
			}
		}

		// Pass a null filter because we already did the filter work above.
		return params(paramList, null);
	}

	public OAuthSigner params(List<String[]> paramList, ParamFilter filter) {
		if (paramList == null || paramList.isEmpty())
			return this;

		// Filter the param list if a filter was provided.
		if (filter != null) {
			for (int i = 0, size = paramList.size(); i < size; i++) {
				String[] pair = paramList.get(i);

				if (pair != null
						&& !filter.include(pair[NAME_INDEX], pair[VALUE_INDEX]))
					paramList.remove(i);
			}
		}

		// URL-Encode all the name/value pairs.
		for (int i = 0, size = paramList.size(); i < size; i++) {
			String[] pair = paramList.get(i);

			// Ensure the 'name' isn't null before encoding.
			if (pair[NAME_INDEX] != null) {
				try {
					pair[NAME_INDEX] = URLEncoder.encode(pair[NAME_INDEX],
							"UTF-8");

					// Only encode the 'value' if not null and name was encoded.
					if (pair[VALUE_INDEX] != null) {
						pair[VALUE_INDEX] = URLEncoder.encode(
								pair[VALUE_INDEX], "UTF-8");
					}
				} catch (Exception e) {
					e.printStackTrace();
					// no-op, continue to next pair.
				}
			}
		}

		// Now sort the list of encoded name/value pairs
		Collections.sort(paramList, COMPARATOR);

		this.paramList = paramList;
		return this;
	}

	public String sign(Algorithm algorithm, String secretKey)
			throws IllegalStateException, IllegalArgumentException {
		return sign(algorithm, secretKey, null);
	}

	public String sign(Algorithm algorithm, String secretKey, String tokenKey)
			throws IllegalStateException, IllegalArgumentException {
		if (requestMethod == null)
			throw new IllegalStateException(
					"Uninitialized Signer: Valid requestMethod has not been set.");
		if (baseURI == null)
			throw new IllegalStateException(
					"Uninitialized Signer: Valid baseURI has not been set.");
		if (paramList == null)
			throw new IllegalStateException(
					"Uninitialized Signer: Valid parameters have not been set.");

		if (algorithm == null)
			throw new IllegalArgumentException("algorithm cannot be null");
		if (secretKey == null || secretKey.length() == 0)
			throw new IllegalArgumentException(
					"secretKey cannot be null or empty");

		StringBuilder buffer = new StringBuilder(256);

		/*
		 * Begin building the signature base string according to:
		 * http://tools.ietf.org/html/rfc5849#section-3.4.1.1
		 */
		buffer.append(requestMethod).append('&');
		buffer.append(baseURI).append('&');

		/*
		 * Iterate over all the sorted pairings, concat'ing the names and values
		 * with each other as "name=value" and then each pairing with each other
		 * with a '&' character.
		 */
		for (int i = 0, size = paramList.size(); i < size; i++) {
			String[] pair = paramList.get(i);

			// Skip pairs with any null values
			if (pair[NAME_INDEX] != null && pair[VALUE_INDEX] != null)
				buffer.append(pair[NAME_INDEX]).append('=')
						.append(pair[VALUE_INDEX]).append('&');
		}

		// Trim the last spurious '&' that was appended
		buffer.setLength(buffer.length() - 1);

		byte[] hash = null;
		StringBuilder keyBuffer = new StringBuilder(64);

		try {
			/*
			 * Begin building the key signature according to:
			 * http://tools.ietf.org/html/rfc5849#section-3.4.2
			 */
			keyBuffer.append(URLEncoder.encode(secretKey, "UTF-8")).append('&');

			// Append tokenKey is included (3 legged OAuth)
			if (tokenKey != null)
				keyBuffer.append(URLEncoder.encode(tokenKey, "UTF-8"));

			SecretKey key = new SecretKeySpec(keyBuffer.toString().getBytes(),
					algorithm.name);
			Mac mac = Mac.getInstance(algorithm.name);

			mac.init(key);

			// Generate the actual HMAC
			hash = mac.doFinal(buffer.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Per the OAuth HMAC spec, return the hash Base64 encoded or null if we
		 * failed to create one:
		 * http://tools.ietf.org/html/rfc5849#section-3.4.2
		 */
		return (hash == null ? null : Base64.encodeBytes(hash));
	}

	public interface ParamFilter {
		public boolean include(String name, String value);
	}

	/**
	 * Comparator used to process the <code>String[]</code> of length 2 used by
	 * this API to represent a name/value pair.
	 * 
	 * @author Riyad Kalla (software@thebuzzmedia.com)
	 */
	static class ParameterComparator implements Comparator<String[]> {
		/**
		 * Used internally by {@link OAuthSigner} to sort the params before
		 * normalizing them according to the OAuth spec.
		 * <p/>
		 * Names are compared first and then values only if the names are equal.
		 * 
		 * @returns <code>-1</code> if <code>param1</code> is &lt;
		 *          <code>param2</code>, <code>0</code> if both params are
		 *          considered equal or <code>1</code> if <code>param1</code> is
		 *          &gt; <code>param2</code>.
		 * 
		 * @throws RuntimeException
		 *             if any of the <code>String[]</code> processed have
		 *             lengths that are not equal to 2.
		 */
		public int compare(String[] param1, String[] param2)
				throws RuntimeException {
			// Begin with the assumption they are equal.
			int value = 0;

			// Only continue checking if they ARE NOT equal.
			if (param1 != param2) {
				if (param1 == null && param2 != null)
					value = -1;
				else if (param1 != null && param2 == null)
					value = 1;
				else {
					// Sanity-check
					if (param1.length != 2 && param2.length != 2)
						throw new RuntimeException(
								"String[] parameter values included in the parameterList have mismatching lengths. All String[] must be of length 2, with param name at index 0 and param value at index 1.");

					// First, compare the names.
					value = param1[NAME_INDEX].compareTo(param2[NAME_INDEX]);

					// Only if the names were the same, should we compare the
					// values.
					if (value == 0)
						value = param1[VALUE_INDEX]
								.compareTo(param2[VALUE_INDEX]);
				}

			}

			return value;
		}
	}
}