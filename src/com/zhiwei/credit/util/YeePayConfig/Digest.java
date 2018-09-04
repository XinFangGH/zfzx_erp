package com.zhiwei.credit.util.YeePayConfig;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {

	public static String hmacSign(String aValue, String charset) {
		try {
			byte[] input = null;
			if (charset == null) {
				input = aValue.getBytes();
			} else {
				input = aValue.getBytes(charset);
			}
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(input);
			return toHex(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

}
