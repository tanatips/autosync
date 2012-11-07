package SecurityNew;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import th.or.nectec.ffc.security.Message_Digest;



public class FFC_Algorithm {
	public static final int KEY_LENGTH = 262144;
	private static String Key = "";
	FFC_Algorithm(String key){
			String key2;
	        MessageDigest md;
	        if(key.length()<=8)
	        {
	        	Message_Digest MD_5 = new Message_Digest();
	        	try {
					key = MD_5.MD5(key);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
	        }
	        byte[] byteData = null;
			try {
				md = MessageDigest.getInstance("SHA-512");
			
				
			md.update(key.getBytes());
			byteData = md.digest();
			} catch (NoSuchAlgorithmException e) {				
				e.printStackTrace();
			}
		//System.out.println("Key = "+byteData.toString()+ " lenght="+byteData.length);
	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	key2 = hexString.toString();
//	    	Log.w("FFC", "key2 = "+key2.length());
	    	Key = key2;
	    	for(int i=0;i<=10;i++){
	    		Key = Key+Key;
                        //System.out.println("key"+i+" = "+key);
//	    		Log.w("FFC", "KEY_NEW "+i+Key);
	    	}
	
}
	public static byte[] encryptByte(byte[] in) {
		byte[] keyByte = FFC_Algorithm.Key.getBytes();
		byte[] out = new byte[keyByte.length];
		for (int i = 0; i < keyByte.length; i++) {
				out[i] = (byte) (in[i] ^ keyByte[i]);
		}
		return out;
	}

}
