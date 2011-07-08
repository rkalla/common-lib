package com.thebuzzmedia.common.util;

/**
 * Utility class used to provide common functionality utilized in dealing with
 * OAuth services; specifically the HMAC generation/verification.
 * <p/>
 * This class is not a general OAuth client library meant to communicate with an
 * OAuth-secured service, it is rather the common functions (under the covers)
 * you would need to perform inside of a client library in order to format,
 * encode and encrypt your HMAC signature used to verify an OAuth-secured
 * request.
 * <p/>
 * It is common for online APIs today to secure communications with some form of
 * HMAC-based authentication (e.g. Amazon Web Services) or specifically
 * implement 2-legged OAuth which is almost identical to what AWS uses, but the
 * format and concatenation of the parameters is a bit different.
 * <p/>
 * This class adheres to the OAuth spec to make building OAuth-compliant APIs
 * easier.
 * 
 * @author Riyad Kalla (software@thebuzzmedia.com)
 * @since 2.3
 * @see <a href="http://en.wikipedia.org/wiki/HMAC">Definition of HMAC</a>
 * @see <a href="http://tools.ietf.org/html/rfc5849#section-3.4.2">OAuth HMAC
 *      Spec</a>
 */
public class OAuthUtils {
	public enum Algorithm {
		MD5("HmacMD5"), SHA1("HmacSHA1"), SHA256("HmacSHA256"), SHA512(
				"HmacSHA512");

		private String name;

		private Algorithm(String name) {
			this.name = name;
		}
	}
}