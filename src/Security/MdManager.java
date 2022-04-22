/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import sun.misc.BASE64Encoder;
import java.util.Base64;
/**
 *
 * @author watcharakon
 */
public class MdManager {

    public static byte[] getSha256byte(String input) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSha256String(String input) {
        byte[] bSha256 = getSha256byte(input);
        if (bSha256 != null) {
          
            String sSha256 = Base64.getEncoder().encodeToString(bSha256);
            return sSha256.trim();
        } else {
            return null;
        }
    }
}
