import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Token {
	String userRequest;

	String buildHash(String sharedSecretArg, String apikeyArg){

	    String sourceString = ""; // shared secret + fields in correct format
	    
	    String sharedSecret = sharedSecretArg;
	    String apikey = apikeyArg;
	    String resourcePath="cd/ForExInquiry";
	    userRequest= "{"+
	                    "\"SystemsTraceAuditNumber\": 451012,"+
	                    "\"RetrievalReferenceNumber\": \"430015451012\","+
	                    "\"AcquiringBin\": 409999,"+
	                    "\"AcquirerCountryCode\": \"101\","+
	                    "\"DestinationCurrencyCode\": \"974\","+
	                    "\"SourceCurrencyCode\": \"124\","+
	                    "\"SourceAmount\": \"251.75\","+
	                    "\"CardAcceptor\": {"+
	                        "\"Name\": \"Mr Smith\","+
	                        "\"TerminalId\": \"12332\","+
	                        "\"IdCode\": \"1014\","+
	                        "\"Address\": {"+
	                            "\"City\": \"San Francisco\","+
	                            "\"State\": \"CA\","+
	                            "\"County\": \"075\","+
	                            "\"Country\": \"USA\","+
	                            "\"ZipCode\": \"56913\""+
	                            "}"+
	                        "}"+
	                    "}";

	    

	    //add legit timestamp stuff
	    int timeStamp = 0;

	    sourceString = sharedSecret + timeStamp + resourcePath + userRequest;
	    
	    //I Know, I still need to add the Library
	    
	    MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    try {
			md.update(sourceString.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Change this to "UTF-16" if needed
	    byte[] hash = md.digest();

	    String xpayToken = "x:"+timeStamp+":"+hash;

	    //x:UNIX_UTC_Timestamp:SHA256_hash

	    System.out.println("returned xpayToken: " + xpayToken);

	    return xpayToken;


	}

}
