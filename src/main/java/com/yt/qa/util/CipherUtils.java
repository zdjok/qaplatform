package com.yt.qa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author zhengdejing
 *
 */

@Component
public class CipherUtils {
	Logger logger = Logger.getLogger(CipherUtils.class);

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public String encodedByMd5(String username, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestBytes = md.digest((password + username.substring(0, username.indexOf("@"))).getBytes());
			return byteArrayToHexString(digestBytes);
		} catch (NoSuchAlgorithmException e) {
			logger.debug("算法不存在。");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字节数组转成16进制形式
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(byteToHexString(b[i]));
		}
		return sb.toString();
	}

	
	/**
	 * 将字节转换成16进制形式
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
