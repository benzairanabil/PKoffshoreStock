package dev.procheck.offshore.stock.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author zchumager
 */
public class EncryptionPassword {

	public static String ENC_DEC_KEY = "G0y1@80iYAu7#IsF";
	Cipher ecipher;
	Cipher dcipher;

	public EncryptionPassword() throws Exception {
		ecipher = Cipher.getInstance("AES");
		dcipher = Cipher.getInstance("AES");
		SecretKey key = new SecretKeySpec(ENC_DEC_KEY.getBytes(), "AES");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		dcipher.init(Cipher.DECRYPT_MODE, key);
	}

	public String encrypt(String str) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");

		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);

		// Encode bytes to base64 to get a string
		return Base64.getEncoder().encodeToString(enc);
		
	}

	public String decrypt(String str) throws Exception {
		// Decode base64 to get bytes
		byte[] dec = Base64.getDecoder().decode(str);

		byte[] utf8 = dcipher.doFinal(dec);

		// Decode using utf-8
		return new String(utf8, "UTF8");
	}
}