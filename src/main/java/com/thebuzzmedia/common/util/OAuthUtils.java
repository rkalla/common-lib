package com.thebuzzmedia.common.util;

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

/**
 * Class used to provide utility methods used for the HMAC/"signature"
 * generation used by OAuth and other equivalent HMAC-based services.
 * <p/>
 * The easiest way to use this class is to make calls to
 * {@link #generateSignature(String, String, Map, String, String, Algorithm)} or
 * {@link #generateSignature(String, String, List, String, String, Algorithm)}.
 * Both methods will delegate to all other methods to prepare the arguments
 * correctly and return the resulting Base64-encoded signature ready for
 * inclusion in an API call.
 * <p/>
 * A popular method for securing HTTP-based APIs online has been to utilize an
 * HMAC signature generated from the details of the request and sent along to
 * the server where, using the client's secret key, recalculates the same HMAC
 * to verify the integrity of the request.
 * <p/>
 * This is typically referred to as "2-legged OAuth".
 * <p/>
 * The process of combining all the parameters in a request, encoding them and
 * generating a signature is a well-defined and very specific process; this
 * utility class implements each of those stages used for signature generation
 * to make implementing the logic much easier.
 * <p/>
 * As mentioned above the easiest thing to do is call one of the
 * <code>Map</code> or <code>List</code>-based <code>generateSignature</code>
 * methods with all the arguments you want included in the signature. Internally
 * those methods delegate out, in-order, to the following methods:
 * <ol>
 * <li><code>normalizeParameters</code> - To prepare all the params for
 * inclusion in the final signature base string.</li>
 * <li><code>createSignatureBaseString</code> - Combine all the values into the
 * well-defined "signature base string" representation as defined by the OAuth
 * spec.</li>
 * <li><code>generateSignature</code> - To do the actual HMAC generation using
 * the given algorithm against the parameters.</li>
 * </ol>
 * This class is not a general OAuth client library meant to communicate with an
 * OAuth-secured service, it is rather the common functions (under the covers)
 * you would need to perform inside of a client library in order to format,
 * encode and encrypt your HMAC signature used to verify an OAuth-secured
 * request.
 * <p/>
 * This class adheres to the OAuth spec to make building OAuth-compliant APIs
 * easier.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 * @since 2.3
 * 
 * @see <a href="http://en.wikipedia.org/wiki/HMAC">Definition of HMAC</a>
 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth HMAC
 *      Spec</a>
 */
public class OAuthUtils {
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
	 * Enum used to define the different strengths of hashing algorithms used
	 * for generating the signature.
	 */
	public enum Algorithm {
		MD5("HmacMD5"), SHA1("HmacSHA1"), SHA256("HmacSHA256"), SHA512(
				"HmacSHA512");

		private String name;

		private Algorithm(String name) {
			this.name = name;
		}
	}

	/**
	 * Normalizes the parameters contained within the given parameter map
	 * according to the OAuth <a
	 * href="http://tools.ietf.org/html/rfc5849#section-3.4.1.3.2">parameter
	 * normalization rules</a> and appending the resultant normalized string of
	 * name/value pairs to the given append buffer.
	 * <p/>
	 * Names that have multiple values associated with them (1 name and 1 value
	 * String[] with a size greater than 1) will result in multiple name/value
	 * pairs with the same name being appended to the normalize string (standard
	 * query-string processing).
	 * <p/>
	 * After getting the values out of the map and flattening them out (for
	 * names with multiple values) this method delegates to
	 * {@link #normalizeParameters(List, StringBuilder)} to encode, sort and
	 * then create the normalized string that is appended to the buffer.
	 * 
	 * @param parameterMap
	 *            The map of parameters to normalize (this is typically meant to
	 *            be the <code>parameterMap</code> directly from the
	 *            <code>HttpServletRequest</code> in a web application, but it
	 *            can really be any map of name/value[] pairings.)
	 * @param appendBuffer
	 *            The buffer that will have the normalized string appended to.
	 * 
	 * @throws IllegalArgumentException
	 *             if any arguments are <code>null</code>.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.1.3.2">OAuth
	 *      Parameter Normalization Spec</a>
	 */
	public static void normalizeParameters(Map<String, String[]> parameterMap,
			StringBuilder appendBuffer) throws IllegalArgumentException {
		if (parameterMap == null)
			throw new IllegalArgumentException("parameterMap cannot be null");
		if (appendBuffer == null)
			throw new IllegalArgumentException("appendBuffer");

		// Short-circuit if there is nothing to do.
		if (parameterMap.isEmpty())
			return;

		/*
		 * We use an array to store the interim list of name/value pairings and
		 * not a static array the same size of the map, because it is possible
		 * that the map contains a single name with multiple values that will be
		 * flattened out into n+1 values in the resulting paramList.
		 */
		List<String[]> paramList = new ArrayList<String[]>(parameterMap.size());
		Iterator<Entry<String, String[]>> entries = parameterMap.entrySet()
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

				// Add the name/value pair to our list.
				paramList.add(new String[] { name, value });
			}
		}

		/*
		 * Delegate to normalize-list to perform the parameter encoding, then
		 * sorting then building the resultant string.
		 */
		normalizeParameters(paramList, appendBuffer);
	}

	/**
	 * Normalizes the parameters contained within the given parameter list
	 * according to the OAuth <a
	 * href="http://tools.ietf.org/html/rfc5849#section-3.4.1.3.2">parameter
	 * normalization rules</a> and appending the resultant normalized string of
	 * name/value pairs to the given append buffer.
	 * <p/>
	 * The <code>String[]</code> values contained in the list must be of length
	 * 2 and have the 'name' stored at index position 0 and 'value' stored at
	 * index position 1.
	 * 
	 * @param parameterList
	 *            The list of parameters to normalize (this method is meant to
	 *            be easily used by APIs parsing out values from headers into a
	 *            small data structure to be normalized by this method, but it
	 *            can really be any list of name/value pairings.)
	 * @param appendBuffer
	 *            The buffer that will have the normalized string appended to.
	 * 
	 * @throws IllegalArgumentException
	 *             if any arguments are <code>null</code>.
	 * 
	 * @see #NAME_INDEX
	 * @see #VALUE_INDEX
	 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.1.3.2">OAuth
	 *      Parameter Normalization Spec</a>
	 */
	public static void normalizeParameters(List<String[]> parameterList,
			StringBuilder appendBuffer) throws IllegalArgumentException {
		if (parameterList == null || parameterList.isEmpty())
			throw new IllegalArgumentException(
					"parameterList cannot be null or empty");
		if (appendBuffer == null)
			throw new IllegalArgumentException("appendBuffer cannot be null");

		// URL-Encode all the name/value pairs.
		for (int i = 0, size = parameterList.size(); i < size; i++) {
			String[] pair = parameterList.get(i);

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
		Collections.sort(parameterList, COMPARATOR);

		/*
		 * Iterate over all the sorted pairings, concat'ing the names and values
		 * with each other as "name=value" and then each pairing with each other
		 * with a '&' character.
		 */
		for (int i = 0, size = parameterList.size(); i < size; i++) {
			String[] pair = parameterList.get(i);

			// Skip pairs with any null values
			if (pair[NAME_INDEX] != null && pair[VALUE_INDEX] != null)
				appendBuffer.append(pair[NAME_INDEX]).append('=')
						.append(pair[VALUE_INDEX]).append('&');
		}

		// Trim the last spurious '&' that was appended
		appendBuffer.setLength(appendBuffer.length() - 1);
	}

	/**
	 * Used to create what OAuth refers to as the <a
	 * href="http://tools.ietf.org/html/rfc5849#section-3.4.1.1">signature base
	 * string</code> used as the "text" input to the singing process when the
	 * signature (HMAC) is eventually generated.
	 * 
	 * @param httpMethod
	 *            The HTTP method (GET, PUT, POST, DELETE, etc.) from the
	 *            request to the server. This value <strong>must be
	 *            uppercase</strong>.
	 * @param requestURI
	 *            The "stem" of the request that came into the server; this is
	 *            the portion of the request that includes the scheme, domain
	 *            and path up to, but excluding the '?' and the query string
	 *            after it. From the Servlet API this is the "requestURI"
	 *            property on the request.
	 * @param parameterMap
	 *            A map of the parameters submitted to the server from the
	 *            client. From the Servlet API this is the "parameterMap"
	 *            property on the request.
	 * 
	 * @return a new buffer that the signature base string was stored in during
	 *         generation.
	 * 
	 * @throws IllegalArgumentException
	 *             if any arguments are <code>null</code>.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.1.1">OAuth
	 *      Signature Base String Spec</a>
	 */
	public static StringBuilder createSignatureBaseString(String httpMethod,
			String requestURI, Map<String, String[]> parameterMap)
			throws IllegalArgumentException {
		return createSignatureBaseString(httpMethod, requestURI, parameterMap,
				null);
	}

	/**
	 * Used to create what OAuth refers to as the <a
	 * href="http://tools.ietf.org/html/rfc5849#section-3.4.1.1">signature base
	 * string</code> used as the "text" input to the singing process when the
	 * signature (HMAC) is eventually generated.
	 * 
	 * @param httpMethod
	 *            The HTTP method (GET, PUT, POST, DELETE, etc.) from the
	 *            request to the server. This value <strong>must be
	 *            uppercase</strong>.
	 * @param requestURI
	 *            The "stem" of the request that came into the server; this is
	 *            the portion of the request that includes the scheme, domain
	 *            and path up to, but excluding the '?' and the query string
	 *            after it. From the Servlet API this is the "requestURI"
	 *            property on the request.
	 * @param parameterList
	 *            A list of the parameters submitted to the server from the
	 *            client. All <code>String[]</code> items need to be of length 2
	 *            and contain the 'name' in the 0-index position and 'value' in
	 *            the 1-index position.
	 * 
	 * @return a new buffer that the signature base string was stored in during
	 *         generation.
	 * 
	 * @throws IllegalArgumentException
	 *             if any arguments are <code>null</code>.
	 * 
	 * @see #NAME_INDEX
	 * @see #VALUE_INDEX
	 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.1.1">OAuth
	 *      Signature Base String Spec</a>
	 */
	public static StringBuilder createSignatureBaseString(String httpMethod,
			String requestURI, List<String[]> parameterList)
			throws IllegalArgumentException {
		return createSignatureBaseString(httpMethod, requestURI, null,
				parameterList);
	}

	// http://tools.ietf.org/html/rfc5849#section-3.4.1.1
	private static StringBuilder createSignatureBaseString(String httpMethod,
			String requestURI, Map<String, String[]> parameterMap,
			List<String[]> parameterList) throws IllegalArgumentException {
		if (httpMethod == null || httpMethod.length() == 0)
			throw new IllegalArgumentException(
					"httpMethod cannot be null or empty and must be a valid HTTP method (GET, PUT, POST, DELETE, etc.)");
		if (requestURI == null || requestURI.length() == 0)
			throw new IllegalArgumentException(
					"requestURI cannot be null or empty");

		StringBuilder appendBuffer = new StringBuilder(256);

		// Builder result according to OAuth spec
		appendBuffer.append(httpMethod).append('&');
		appendBuffer.append(requestURI).append('&');

		// Append the params if there are any.
		if (parameterMap != null && !parameterMap.isEmpty())
			normalizeParameters(parameterMap, appendBuffer);
		else if (parameterList != null && !parameterList.isEmpty())
			normalizeParameters(parameterList, appendBuffer);

		return appendBuffer;
	}

	/**
	 * Used to sign or generate the HMAC from the given signature base string
	 * using the given secret key (private/public key) and optionally the
	 * <code>tokenKey</code> (for 3-legged OAuth) with the given strength of
	 * hashing algorithm according to the <a
	 * href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth signing
	 * spec</a>.
	 * <p/>
	 * If you are calculating a signature for a 2-legged OAuth communication
	 * process, simply leave <code>tokenKey</code> <code>null</code>.
	 * <p/>
	 * Callers can use one of the other 2 <code>generateSignature</code> methods
	 * to have them automatically create the <code>signatureBaseString</code>
	 * for them.
	 * 
	 * @param signatureBaseString
	 *            The "signature base string" generated by one of the
	 *            <code>createSignatureBaseString</code> methods.
	 * @param secretKey
	 *            The secret key (private/public key method) used to hash the
	 *            signature with.
	 * @param tokenKey
	 *            The token key given by the authentication OAuth service in a
	 *            3-legged OAuth flow or <code>null</code> if not needed (e.g.
	 *            in a 2-legged OAuth flow).
	 * @param algorithm
	 *            The strength of hashing algorithm to use to sign the
	 *            signature.
	 * 
	 * @return A Base64-encoded signature (or HMAC) of the given arguments ready
	 *         for use with an OAuth service.
	 * 
	 * @throws IllegalArgumentException
	 *             if any of the arguments are <code>null</code>.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth
	 *      Signature Spec</a>
	 */
	public static String generateSignature(StringBuilder signatureBaseString,
			String secretKey, String tokenKey, Algorithm algorithm)
			throws IllegalArgumentException {
		if (signatureBaseString == null || signatureBaseString.length() == 0)
			throw new IllegalArgumentException(
					"signatureBaseString cannot be null or empty");
		if (secretKey == null || secretKey.length() == 0)
			throw new IllegalArgumentException(
					"secretKey cannot be null or empty");
		if (algorithm == null)
			throw new IllegalArgumentException("algorithm cannot be null");

		byte[] hash = null;
		StringBuilder keyBuffer = new StringBuilder(128);

		try {
			// First, append the encoded secret
			keyBuffer.append(URLEncoder.encode(secretKey, "UTF-8")).append('&');

			// Append tokenKey is included (3 legged OAuth)
			if (tokenKey != null)
				keyBuffer.append(URLEncoder.encode(tokenKey, "UTF-8"));

			SecretKey key = new SecretKeySpec(keyBuffer.toString().getBytes(),
					algorithm.name);
			Mac mac = Mac.getInstance(algorithm.name);

			mac.init(key);
			hash = mac.doFinal(signatureBaseString.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (hash == null ? null : Base64.encodeBytes(hash));
	}

	/**
	 * Used to sign or generate the HMAC from the given signature base string
	 * using the given secret key (private/public key) and optionally the
	 * <code>tokenKey</code> (for 3-legged OAuth) with the given strength of
	 * hashing algorithm according to the <a
	 * href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth signing
	 * spec</a>.
	 * <p/>
	 * If you are calculating a signature for a 2-legged OAuth communication
	 * process, simply leave <code>tokenKey</code> <code>null</code>.
	 * 
	 * @param httpMethod
	 *            The HTTP method (GET, PUT, POST, DELETE, etc.) from the
	 *            request to the server. This value <strong>must be
	 *            uppercase</strong>.
	 * @param requestURI
	 *            The "stem" of the request that came into the server; this is
	 *            the portion of the request that includes the scheme, domain
	 *            and path up to, but excluding the '?' and the query string
	 *            after it. From the Servlet API this is the "requestURI"
	 *            property on the request.
	 * @param parameterMap
	 *            A map of the parameters submitted to the server from the
	 *            client. From the Servlet API this is the "parameterMap"
	 *            property on the request.
	 * @param secretKey
	 *            The secret key (private/public key method) used to hash the
	 *            signature with.
	 * @param tokenKey
	 *            The token key given by the authentication OAuth service in a
	 *            3-legged OAuth flow or <code>null</code> if not needed (e.g.
	 *            in a 2-legged OAuth flow).
	 * @param algorithm
	 *            The strength of hashing algorithm to use to sign the
	 *            signature.
	 * 
	 * @return A Base64-encoded signature (or HMAC) of the given arguments ready
	 *         for use with an OAuth service.
	 * 
	 * @throws IllegalArgumentException
	 *             if any of the arguments are <code>null</code>.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth
	 *      Signature Spec</a>
	 */
	public static String generateSignature(String httpMethod,
			String requestURI, Map<String, String[]> parameterMap,
			String secretKey, String tokenKey, Algorithm algorithm) {
		// Generate the signature base string
		StringBuilder appendBuffer = createSignatureBaseString(httpMethod,
				requestURI, parameterMap);

		// Sign and return the result to the caller
		return generateSignature(appendBuffer, secretKey, tokenKey, algorithm);
	}

	/**
	 * Used to sign or generate the HMAC from the given signature base string
	 * using the given secret key (private/public key) and optionally the
	 * <code>tokenKey</code> (for 3-legged OAuth) with the given strength of
	 * hashing algorithm according to the <a
	 * href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth signing
	 * spec</a>.
	 * <p/>
	 * If you are calculating a signature for a 2-legged OAuth communication
	 * process, simply leave <code>tokenKey</code> <code>null</code>.
	 * 
	 * @param httpMethod
	 *            The HTTP method (GET, PUT, POST, DELETE, etc.) from the
	 *            request to the server. This value <strong>must be
	 *            uppercase</strong>.
	 * @param requestURI
	 *            The "stem" of the request that came into the server; this is
	 *            the portion of the request that includes the scheme, domain
	 *            and path up to, but excluding the '?' and the query string
	 *            after it. From the Servlet API this is the "requestURI"
	 *            property on the request.
	 * @param parameterList
	 *            A list of the parameters submitted to the server from the
	 *            client. All <code>String[]</code> items need to be of length 2
	 *            and contain the 'name' in the 0-index position and 'value' in
	 *            the 1-index position.
	 * @param secretKey
	 *            The secret key (private/public key method) used to hash the
	 *            signature with.
	 * @param tokenKey
	 *            The token key given by the authentication OAuth service in a
	 *            3-legged OAuth flow or <code>null</code> if not needed (e.g.
	 *            in a 2-legged OAuth flow).
	 * @param algorithm
	 *            The strength of hashing algorithm to use to sign the
	 *            signature.
	 * 
	 * @return A Base64-encoded signature (or HMAC) of the given arguments ready
	 *         for use with an OAuth service.
	 * 
	 * @throws IllegalArgumentException
	 *             if any of the arguments are <code>null</code>.
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth
	 *      Signature Spec</a>
	 */
	public static String generateSignature(String httpMethod,
			String requestURI, List<String[]> parameterList, String secretKey,
			String tokenKey, Algorithm algorithm) {
		// Generate the signature base string
		StringBuilder appendBuffer = createSignatureBaseString(httpMethod,
				requestURI, parameterList);

		// Sign and return the result to the caller
		return generateSignature(appendBuffer, secretKey, tokenKey, algorithm);
	}

	/**
	 * Comparator used to process the <code>String[]</code> of length 2 used by
	 * this API to represent a name/value pair.
	 * 
	 * @author Riyad Kalla (software@thebuzzmedia.com)
	 */
	static class ParameterComparator implements Comparator<String[]> {
		/**
		 * Used internally by
		 * {@link OAuthUtils#normalizeParameters(List, StringBuilder)} to sort
		 * the resulting param list before returning it.
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