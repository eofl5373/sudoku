/*
 * @(#)CryptoUtil.java
 */
package com.bnk.plus.commons.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * 암복호화 유틸 클래스
 * @author kschoi
 */
public class CryptUtil {

	/**
	 * 생성자, 외부에서 객체를 인스턴스화 할 수 없도록 설정
	 */
	private CryptUtil() {
	}
	
	/**
	 * Sha 256으로 특정 메시지를 해쉬 암호화하여 리턴한다.
	 * 		*Spring Security의 hash 암호화를 이용하므로 라이브러리 추가 필요
	 * 
	 * @param message
	 * @return 암호화된 Hash Message
	 */
	public static String encryptSha256(String message){
		return encryptSha256(message, null);
	}
	
	/**
	 * Sha 256으로 특정 메시지에 Salt를 추가, 메시지를 해쉬 암호화하여 리턴한다.
	 * 		*Spring Security의 hash 암호화를 이용하므로 라이브러리 추가 필요
	 * 
	 * @param message
	 * @return 암호화된 Hash Message
	 */
	public static String encryptSha256(String message, String salt){
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		if (StringUtil.isEmpty(message))return null;
		return encoder.encodePassword(message, salt);
	}
	
	
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	/**
	 * AES 256 으로 암호화
	 * 
	 * @param message
	 * @param key
	 * 
	 * @return
	 * 
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException 
	 */
    public static String encryptAES256(String message, String key) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
    	byte[] textBytes = message.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	     SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	     Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		return Base64.encodeBase64String(cipher.doFinal(textBytes));
    }
    
    /**
     * AES 256 으로 복호화
     * 
     * @param encMessage
     * @param key
     * 
     * @return
     * 
     * @throws java.io.UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decryptAES256(String encMessage, String key) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    	byte[] textBytes = Base64.decodeBase64(encMessage);
		//byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		return new String(cipher.doFinal(textBytes), "UTF-8");
    }
}










