package SecurityNew;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;




public class SecurityManager {

	public static final String DATABASE_FOLDER_PATH = "./FFC/db_tmp";
	public static final String FILE_CHIPER_DB = "mJHCIS.sdb";
	public static final String FILE_PLAIN_DB = "mJHCIS.db";
	public static String DATABASE_KEY = "wis@nectec";

	public boolean decryptDatabaseFile() {
		boolean success;
		File sdb = new File(DATABASE_FOLDER_PATH, FILE_CHIPER_DB);
		File db = new File(DATABASE_FOLDER_PATH, FILE_PLAIN_DB);
		if (sdb.exists()) {
			// Found encrypted database file then decrypt iT!
			// Create 'db' to be instance of Decrypt Database file
			try {		
				if (db.exists())
					return true; // Not need to decrypt sdb
				else
					db.createNewFile();
				
				
				//Change Algorithm
				Crypto crypter = new Crypto(DATABASE_KEY, Crypto.ALGORITHM_FFC);
				//Crypto crypter = new Crypto(DATABASE_KEY, Crypto.ALGORITHM_AES);
				crypter.decrypt(new FileInputStream(sdb), new FileOutputStream(
						db));
				success = true;
			} catch (FileNotFoundException e) {
				// this shold not be happen
				System.out.println("Not Found File");
				success = false;
			} catch (IOException e) {
				// Just say that unsuccess
				System.out.println("Can't create new File for Encrypt");
				success = false;
			}
		} else if(db.exists()){
			// found Plain database file
			success = true;
		}
		else {
			// Not found encrypted database file - mJHICIS.sdb
			success = false;
		}
		return success;
	}

	public boolean encryptDatabaseFile() {
		boolean success;
		File db = new File(DATABASE_FOLDER_PATH, FILE_PLAIN_DB);
		if (db.exists()) {
			// Found database file then encrypt iT!
			// Create 'sdb' to be instance of Encrypt Database file
			try {
				File sdb = new File(DATABASE_FOLDER_PATH, FILE_CHIPER_DB);
				if (sdb.exists())
					sdb.delete();
				sdb.createNewFile();

				//Crypto crypter = new Crypto(DATABASE_KEY, Crypto.ALGORITHM_AES);
				Crypto crypter = new Crypto(DATABASE_KEY, Crypto.ALGORITHM_FFC);
				crypter.encrypt(new FileInputStream(db), new FileOutputStream(
						sdb));
				// After encryption done! delete unsecure file for security
				db.delete();
				success = true;
			} catch (FileNotFoundException e) {
				// this shold not be happen
				System.out.println("Not Found File");
				success = false;
			} catch (IOException e) {
				// Just say that unsuccess
				System.out.println("can't create instance for db file");
				success = false;

			}
			success = true;
		} else {
			// Not found encrypted database file - mJHICIS.sdb
			success = false;
		}
		return success;
	}
}
