package SecurityNew;

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



import SecurityNew.Algorithm_Function;

public class Crypto {
	Cipher ecipher;
	Cipher dcipher;
	public static final int ALGORITHM_AES = 0;
	public static final int ALGORITHM_DES = 1;
	public static final int ALGORITHM_TEA = 2;
	public static final int ALGORITHM_FFC = 3;
	public static final String AES_INSTANCE = "AES/CBC/PKCS5Padding";
	public static final String DES_INSTANCE = "DES/CBC/PKCS5Padding";

	static final byte[] mAESiv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04,
			0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
	static final byte[] mDESiv = new byte[] { (byte) 0x8E, 0x12, 0x39,
			(byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };

	private int mCryptoMode;
	FFC_Algorithm FFC;

	Algorithm_Function Function = new Algorithm_Function();

	public Crypto(String key, int Algorithm) {
		mCryptoMode = Algorithm;
		switch (Algorithm) {
		case ALGORITHM_AES: {
			SecretKeySpec skey = new SecretKeySpec(getMD5(key), "AES");
			this.setupCrypto(skey, Function.function(Function.AES,
					Function.CBC, Function.NoPadding), mAESiv);
			break;
		}
		case ALGORITHM_DES: {
			byte[] keyData = key.getBytes();
			SecretKeySpec skey = new SecretKeySpec(keyData, 0,
					DESKeySpec.DES_KEY_LEN, "DES");
			this.setupCrypto(skey, DES_INSTANCE, mDESiv);

			break;
		}
		case ALGORITHM_FFC: {
			FFC_Algorithm FFC = new FFC_Algorithm(key);
			System.out.println("Start_FFC_Algorithm");
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

		final int ODD = 0;
		final int EVEN = 1;
		
		long starttime = 0;
		long stoptime =0;
		
		
		try {
			starttime = System.currentTimeMillis();
			// Bytes written to out will be encrypted
			if (mCryptoMode == ALGORITHM_FFC) {
				int read = 1;
				byte[] bytes = new byte[FFC_Algorithm.KEY_LENGTH];
				//System.out.println("key_new length = " + FFC_Algorithm.KEY_LENGTH);
				int round = ODD;
				while ((read = in.read(bytes)) != -1) {
					switch (round) {
					case ODD: {
						byte[] theByteArray = FFC.encryptByte(bytes);
						out.write(theByteArray, 0, read);
						round = EVEN;
					}
						break;
					case EVEN: {
						out.write(bytes, 0, read);
						round = ODD;
					}
						break;
					}
				}
			

			} else {
				out = new CipherOutputStream(out, ecipher);
				// Read in the cleartext bytes and write to out to encrypt
				int numRead = 0;
				while ((numRead = in.read(buf)) >= 0) {
					out.write(buf, 0, numRead);
				}
			}
			in.close();
			out.close();
			
			stoptime = System.currentTimeMillis();
			long elapsed = stoptime - starttime;
			//System.out.println("FFC Encrypt time = " + elapsed);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public void decrypt(InputStream in, OutputStream out) {

		if (mCryptoMode == ALGORITHM_FFC) {
			mCryptoMode = ALGORITHM_FFC;
			encrypt(in, out);
		} else {
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
