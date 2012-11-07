package SecurityNew;

public class Algorithm_Function {
	public static String FUNCTION_OUT = "";
	
//////////Algorithm_TYPE//////////
	String AES = "AES";
	String DES = "DES";

//////////Algorithm_MODE//////////	
	String OFB = "OFB";
	String CFB = "CFB";
	String CBC = "CBC";
//	String ECB = "ECB";
//	String CTR = "CTR";
//	String XTS = "XTS";
//	String OCB = "OCB";
//	String GCM = "GCM";
//	String CCM = "CCM";
//	String EAX = "EAX";
//	String PCBC = "PCBC";
	
	
//////////Algorithm_FUNCTION//////////	
	//function mode
	String NoPadding = "NoPadding";
	String PKCS5Padding = "PKCS5Padding";
	String ISO10126Padding = "ISO10126Padding";
	
	
	public static String function(String Algorithm_TYPE, String Algorithm_MODE, String Algorithm_FUNCTION) {
		FUNCTION_OUT = FUNCTION_OUT + Algorithm_TYPE + "/";
		FUNCTION_OUT = FUNCTION_OUT + Algorithm_MODE + "/";
		FUNCTION_OUT = FUNCTION_OUT + Algorithm_FUNCTION;
		return FUNCTION_OUT;
}
	
}


