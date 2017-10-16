package com.safetys.nbsxs.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 安生密码加密工具
 */
public class SafetysPasswordEncoder {
	
	private static SafetysPasswordEncoder safetysPasswordEncoder = null;

	private static final String SAFETYS_PASS_SALT = "safetys_framework_salt";

	private static MessageDigest md;

	private SafetysPasswordEncoder() {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public static SafetysPasswordEncoder getInstance() {
		if (safetysPasswordEncoder == null)
			safetysPasswordEncoder = new SafetysPasswordEncoder();
		return safetysPasswordEncoder;
	}

	// 加密
	public String encodePassword(String rawPass) {
		// 加盐
		String saltedPass = mergePasswordAndSalt(rawPass, SAFETYS_PASS_SALT,
				false);
		byte[] digest;
		try {
			// 加密
			digest = md.digest(saltedPass.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}
		// 加密后HEX处理且返回
		return new String(Hex.encode(digest));
	}

	// 加盐
	public String mergePasswordAndSalt(String password, Object salt,
			boolean strict) {
		if (password == null) {
			password = "";
		}

		if ((strict)
				&& (salt != null)
				&& (((salt.toString().lastIndexOf("{") != -1) || (salt
						.toString().lastIndexOf("}") != -1)))) {
			throw new IllegalArgumentException(
					"Cannot use { or } in salt.toString()");
		}

		if ((salt == null) || ("".equals(salt))) {
			return password;
		}
		return password + "{" + salt.toString() + "}";
	}

	// 解盐
	public String[] demergePasswordAndSalt(String mergedPasswordSalt) {
		if ((mergedPasswordSalt == null) || ("".equals(mergedPasswordSalt))) {
			throw new IllegalArgumentException(
					"Cannot pass a null or empty String");
		}

		String password = mergedPasswordSalt;
		String salt = "";

		int saltBegins = mergedPasswordSalt.lastIndexOf("{");

		if ((saltBegins != -1)
				&& (saltBegins + 1 < mergedPasswordSalt.length())) {
			salt = mergedPasswordSalt.substring(saltBegins + 1,
					mergedPasswordSalt.length() - 1);
			password = mergedPasswordSalt.substring(0, saltBegins);
		}

		return new String[] { password, salt };
	}

}

// HEX处理
final class Hex {
	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static char[] encode(byte[] bytes) {
		int nBytes = bytes.length;
		char[] result = new char[2 * nBytes];

		int j = 0;
		for (int i = 0; i < nBytes; ++i) {
			result[(j++)] = HEX[((0xF0 & bytes[i]) >>> 4)];

			result[(j++)] = HEX[(0xF & bytes[i])];
		}

		return result;
	}
}
