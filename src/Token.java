import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;


public class Token {
	String userRequest;

	void buildHash(String sharedSecretArg, String apiKeyArg){

		String sourceString = ""; // shared secret + fields in correct format
		String hash="";
		String sharedSecret="";
		String apikey="";
		String resourcePath="";
		String request="";
		String xpayToken="";

		long unixTime = System.currentTimeMillis() / 1000L;

		sharedSecret=sharedSecretArg;
		//resourcePath=args[1];
		//resourcePath="/da/cd/ForExInquiry";
		resourcePath="cd/ForExInquiry";
		//request=args[2];
		request="SystemsTraceAuditNumber=451012&RetrievalReferenceNumber=345678901234&AcquiringBin=409999&AcquirerCountryCode=101&DestinationCurrencyCode=BYR&SourceCurrencyCode=CAD&SourceAmount=251.75&CardAcceptor.Name=Mr Smith&CardAcceptor.TerminalId=12332&CardAcceptor.IdCode=VMT200911026070&CardAcceptor.Address.City=San Francisco&CardAcceptor.Address.State=CA&CardAcceptor.Address.County=075&CardAcceptor.Address.Country=USA&CardAcceptor.Address.ZipCode=56913";

		/*
		request= "{"+
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
		 */
		//apikey="ETS6MO4Q96CE0ZURSHCV21hh0pK6v4HDFy_Ev9WxWUrbnX0pE";
		apikey=apiKeyArg;

		try {
			request=urlEncodeField(request);
		} catch (Exception e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}

		sourceString = (sharedSecret + Long.toString(unixTime) + resourcePath +"apikey="+apikey+ request);

		try {
			hash = Token.sha256Digest(sourceString);
		} catch (Exception e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}

		xpayToken=("x:" + Long.toString(unixTime) + ":" + hash);

		//System.out.println("sharedSecret="+sharedSecret);
		//System.out.println("apikey="+apikey);
		//System.out.println("unixTime="+unixTime);
		//System.out.println("ResourcePath="+resourcePath);
		//System.out.println("sourceString="+sourceString);
		//System.out.println("hash="+hash);
		//System.out.println("request="+request);

		System.out.println("xpayToken="+xpayToken);
	}
	
	public static String sha256Digest(String data) throws SignatureException {
		return getDigest("SHA-256", data, true);
	}

	private static String getDigest(String algorithm, String data, boolean toLower) throws SignatureException {
		try {
			MessageDigest mac = MessageDigest.getInstance(algorithm);
			mac.update(data.getBytes("UTF-8"));
			return toLower ?
			new String(toHex(mac.digest())).toLowerCase() : new String(toHex(mac.digest()));
		} catch (Exception e) {
			throw new SignatureException(e);
		}
	}

	private static String toHex(byte[] bytes) {
		BigInteger bi = new BigInteger(1, bytes);
		return String.format("%0" + (bytes.length << 1) + "X", bi);
	}


	public static String urlEncodeField(String field_to_encode){
	  try {
	    return URLEncoder.encode(field_to_encode,"UTF-8");
	  }
	 catch (UnsupportedEncodingException e) {
	    return field_to_encode;
	  }
	}

}
