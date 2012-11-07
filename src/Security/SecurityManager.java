/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import th.or.nectec.ffc.database.JhcisDatabaseAdapter;

public class SecurityManager {

//public static final String DATABASE_FOLDER_PATH = "C:/FFC/Db_tmp/";
        public static String DATABASE_FOLDER_PATH;
	public static final String FILE_CHIPER_DB = "mJHCIS.sdb";
	public static final String FILE_PLAIN_DB = "mJHCIS.db";
	//public static final String DATABASE_KEY = "wis@nectec";
        public final String DATABASE_KEY;
        
        public static void main(String []arg){
            SecurityManager manager = new SecurityManager("./FFC/Db_tmp/");
            manager.decryptDatabaseFile();
        }

    public SecurityManager(String path) {
        DATABASE_FOLDER_PATH = path;
        DATABASE_KEY = "wis@nectec";
    }

	public boolean decryptDatabaseFile() {
		boolean success;
		File sdb = new File(DATABASE_FOLDER_PATH, FILE_CHIPER_DB);
		File db = new File(DATABASE_FOLDER_PATH, FILE_PLAIN_DB);
		if (sdb.exists()) {
			// Found encrypted database file then decrypt iT!
			// Create 'db' to be instance of Decrypt Database file
			try {		
				if (db.exists()){
                                }
				else
					db.createNewFile();
				Crypto crypter = new Crypto(DATABASE_KEY, Crypto.ALGORITHM_AES);
				crypter.decrypt(new FileInputStream(sdb), new FileOutputStream(
						db));
				success = true;
			} catch (FileNotFoundException e) {
				// this shold not be happen
				//Log.d("FFC", "Not Found File");
				success = false;
			} catch (IOException e) {
				// Just say that unsuccess
				//Log.d("FFC", "Can't create new File for Encrypt");
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
                    System.out.println("mJHCIS.db does exist");
			// Found database file then encrypt iT!
			// Create 'sdb' to be instance of Encrypt Database file
			try {
				File sdb = new File(DATABASE_FOLDER_PATH, FILE_CHIPER_DB);
				if (sdb.exists())
					sdb.delete();
				sdb.createNewFile();

				Crypto crypter = new Crypto(DATABASE_KEY, Crypto.ALGORITHM_AES);
				crypter.encrypt(new FileInputStream(db), new FileOutputStream(
						sdb));
				// After encryption done! delete unsecure file for security
				db.delete();
				success = true;
			} catch (FileNotFoundException e) {
				// this shold not be happen
				//Log.d("FFC", "Not Found File");
				success = false;
			} catch (IOException e) {
				// Just say that unsuccess
				//Log.d("FFC", "can't create instance for db file");
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
