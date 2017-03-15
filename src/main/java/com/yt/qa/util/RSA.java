package com.yt.qa.util;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSA {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final int MAX_DECRYPT_BLOCK = 128;

	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put("RSAPublicKey", publicKey);
		keyMap.put("RSAPrivateKey", privateKey);
		return keyMap;
	}

	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		int len = plainTextData.length;
		int offset = 0;
		int i = 0;
		byte[] cache;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			while (len - offset > 0) {
				if (len - offset > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(plainTextData, offset, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(plainTextData, offset, len - offset);
				}
				i++;
				offset = i * MAX_DECRYPT_BLOCK;
				bos.write(cache, 0, cache.length);
			}

			byte[] result = bos.toByteArray();
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
		int len = cipherData.length;
		int offset = 0;
		int i = 0;
		byte[] cache;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");

			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			while ((len - offset) > 0) {
				if ((len - offset) > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(cipherData, offset, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(cipherData, offset, len - offset);
				}
				bos.write(cache, 0, cache.length);
				i++;
				offset = i * MAX_DECRYPT_BLOCK;
			}

			byte[] decryptedData = bos.toByteArray();

			return decryptedData;
			// return cipher.doFinal(cipherData);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		} finally {
			bos.close();
		}
	}
}
