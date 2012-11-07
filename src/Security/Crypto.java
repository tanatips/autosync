/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	Cipher ecipher;
	Cipher dcipher;
	public static final int ALGORITHM_AES = 0;
	public static final int ALGORITHM_DES = 1;
	public static final int ALGORITHM_TEA = 2;
	public static final String AES_INSTANCE = "AES/CBC/PKCS5Padding";
	public static final String DES_INSTANCE = "DES/CBC/PKCS5Padding";

	static final byte[] mAESiv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04,
			0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
	static final byte[] mDESiv = new byte[] { (byte) 0x8E, 0x12, 0x39,
			(byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };

	private int mCryptoMode;

	public Crypto(String key, int Algorithm) {
		mCryptoMode = Algorithm;
		switch (Algorithm) {
		case ALGORITHM_AES: {
			SecretKeySpec skey = new SecretKeySpec(getMD5(key), "AES");
			this.setupCrypto(skey, AES_INSTANCE, mAESiv);
			break;
		}
		case ALGORITHM_DES: {
			byte[] keyData = key.getBytes();
			SecretKeySpec skey = new SecretKeySpec(keyData, 0,
					DESKeySpec.DES_KEY_LEN, "DES");
			this.setupCrypto(skey, DES_INSTANCE, mDESiv);
			break;
		}
		}
	}

	private void setupCrypto(SecretKey key, String instance, byte[] iv) {
		// Create an 8-byte initialization vector

		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		try {
			// ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher = Cipher.getInstance(instance);
			dcipher = Cipher.getInstance(instance);

			// CBC requires an initialization vector
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Buffer used to transport the bytes from one stream to another
	byte[] buf = new byte[1024];

	public void encrypt(InputStream in, OutputStream out) {

		try {

			// Bytes written to out will be encrypted
			out = new CipherOutputStream(out, ecipher);

			// Read in the cleartext bytes and write to out to encrypt
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			in.close();
			out.close();
		} catch (java.io.IOException e) {
		}

	}

	public void decrypt(InputStream in, OutputStream out) {
		try {
			// Bytes read from in will be decrypted
			in = new CipherInputStream(in, dcipher);

			// Read in the decrypted bytes and write the cleartext to out
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			in.close();
			out.close();
		} catch (java.io.IOException e) {
		}

	}

	private static byte[] getMD5(String input) {
		try {
			byte[] bytesOfMessage = input.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(bytesOfMessage);
		} catch (Exception e) {
			return null;
		}
	}

}
