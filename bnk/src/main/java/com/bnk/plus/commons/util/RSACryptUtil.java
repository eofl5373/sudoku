package com.bnk.plus.commons.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * <pre>
 * RSACryptUtil.java
 * 
 * 사용 방법
 * 1. "generateKeyPair()" 로 KeyPair를 생성한다.
 * 		KeyPair에서는 ".getPublic()", ".getPrivate()"를 추출할 수 있다.
 * 2. Javascript Module로 연동 가능한 public Key Module Set를 추출한다.
 * 		generatePublicKeyModulus();
 * 		generatePublicKeyExponent();
 * 3. Private Key를 DB에 저장하고 추후 다시 생성하기 위한 Key String을 추출한다.
 * 		generatePrivateKeyString();
 * 	3-1. 추출한 Private Key String으로 다시 private를 만들기 위해서는 
 * 		"generatePrivateKey(String privateKeyString)" 메서드를 사용한다.
 * 4. 복호화 : decrypt()
 * </pre>
 *
 * @author ks-choi
 * @date 2015. 8. 31.
 */
public class RSACryptUtil {
	
	/**
	 * <pre>
	 * 1. 설명 : KeyPair 생성
	 * 2. 동작 : 
	 * 3. Input : 
	 * 4. Output : {@link KeyPair} keyPair
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 27.     Administrator         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024, new SecureRandom());
		KeyPair keyPair = generator.genKeyPair();
		return keyPair;
	}
	
	/**
	 * <pre>
	 * 1. 설명 : publicKeyModules
	 * 2. 동작 : Public Key로 Javascript 연동을 위한 publicKeyModulus를 생성한다.
	 * 3. Input : PublicKey publicKey
	 * 4. Output : String publicKeyModulus
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 27.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String generatePublicKeyModulus (PublicKey publicKey) throws Exception{
		String publicKeyModulus = "";
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		publicKeyModulus = publicSpec.getModulus().toString(16);
		return publicKeyModulus;
	}
	/**
	 * <pre>
	 * 1. 설명 : publicKeyExponent
	 * 2. 동작 : Public Key로 Javascript 연동을 위한 publicKeyExponent를 생성한다.
	 * 3. Input : PublicKey publicKey
	 * 4. Output : String publicKeyExponent
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String generatePublicKeyExponent (PublicKey publicKey) throws Exception{
		String publicKeyExponent = "";
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		publicKeyExponent = publicSpec.getPublicExponent().toString(16);
		return publicKeyExponent;
	}
	
	/**
	 * <pre>
	 * 1. 설명 : PublicKey String을 추출한다.
	 * 2. 동작 : PublicKey를 생성하기 위한 KeyString을 추출
	 * 3. Input : PublicKey publicKey
	 * 4. Output : String publicKeyString
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param publicKey
	 * @return
	 */
	public static String generatePublicKeyString (PublicKey publicKey){
		return Base64.encode(publicKey.getEncoded());
	}
	/**
	 * <pre>
	 * 1. 설명 : generatePublicKeyString()로 추출한 String으로 PublicKey를 생성한다.
	 * 2. 동작 : 
	 * 3. Input : {@link String} publicKeyString
	 * 4. Output : {@link PublicKey} publicKey
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     Administrator         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param publicKeyString
	 * @return
	 * @throws Exception
	 */
	public static PublicKey generatePublicKey (String publicKeyString) throws Exception{
		byte[] publicKeyBytes = Base64.decode(publicKeyString.getBytes("utf-8"));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
		PublicKey key = keyFactory1.generatePublic(spec);
		return key;
	}
	
	/**
	 * <pre>
	 * 1. 설명 : PrivateKey String을 추출한다.
	 * 2. 동작 : PrivateKey를 생성하기 위한 KeyString을 추출
	 * 3. Input : {@link PrivateKey} PrivateKey
	 * 4. Output : String privateKeyString
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param privateKey
	 * @return
	 */
	public static String generatePrivateKeyString (PrivateKey privateKey){
		return Base64.encode(privateKey.getEncoded());
	}
	/**
	 * <pre>
	 * 1. 설명 : generatePrivateKeyString()로 추출한 String으로 PrivateKey를 생성한다.
	 * 2. 동작 : 
	 * 3. Input : {@link String} privateKeyString
	 * 4. Output : {@link PrivateKey} privateKey
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param privateKeyString
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey generatePrivateKey (String privateKeyString) throws Exception{
		byte[] privateKeyBytes = Base64.decode(privateKeyString.getBytes("utf-8"));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PrivateKey priv = fact.generatePrivate(keySpec);
		return priv;
	}
	
	/**
	 * <pre>
	 * 1. 설명 : RSA 암호화
	 * 2. 동작 : PublicKey로 암호화한다.
	 * 3. Input :
	 *         PublicKey publicKey
	 *         String plainText
	 * 4. Output : byte
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     Administrator         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param publicKey
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey publicKey, String plainText) throws Exception{
		byte[] input = plainText.getBytes("utf-8");
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherText = cipher.doFinal(input);
		return cipherText;
	}
	/**
	 * <pre>
	 * 1. 설명 : RSA 복호화
	 * 2. 동작 : PrivateKey로 복호화한다.
	 * 3. Input : 
	 *            PrivateKey privateKey
	 *            byte[] chiperText
	 * 4. Output : String DecryptPlainText
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param privateKey
	 * @param chiperText
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(PrivateKey privateKey, byte[] chiperText) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainText = cipher.doFinal(chiperText);
		return new String(plainText);
	}
	/**
	 * <pre>
	 * 1. 설명 : RSA 복호화
	 * 2. 동작 : PrivateKey로 복호화한다.
	 * 3. Input : 
	 *            PrivateKey privateKey
	 *            String chiperStringText
	 * 4. Output : String DecryptPlainText
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 31.     ks-choi         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param privateKey
	 * @param chiperText
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(PrivateKey privateKey, String chiperStringText) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainText = cipher.doFinal(hexToByteArray(chiperStringText));
		return new String(plainText);
	}
	
	/**
     * 16진 문자열을 byte 배열로 변환한다.
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
}
