package com.memo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtills {
	// input: 원본 password
	// output: hassing 된 password
	public static String md5(String message) {
		String encData = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] bytes = message.getBytes();
			md.update(bytes);
			byte[] digest = md.digest();

			for (int i = 0; i < digest.length; i++) {
				encData += Integer.toHexString(digest[i] & 0xff); // 16진수로 변환하는 과정
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encData;
	}
}
