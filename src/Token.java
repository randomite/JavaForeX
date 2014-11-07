
public class Token {
	String userRequest;

	String buildHash(){

	    String sourceString = ""; // shared secret + fields in correct format
	    String hash="";
	    String sharedSecret="FCZvnD}2ikdBhZmrpaOSIya0+m2/Sj{Mpv@L+OOT";
	    String apikey="W869FJ62VVNYBVY73Q3621Jyljzif9DbVYRuY_6wNjyDMubq4";
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
	    hash = CryptoJS.SHA256(sourceString);

	    String xpayToken = "x:"+timeStamp+":"+hash;

	    //x:UNIX_UTC_Timestamp:SHA256_hash

	    System.out.println("returned xpayToken: " + xpayToken);

	    return xpayToken;

	    //String base64String = new Buffer(hash).toString('base64');



	    /*
	    String crypto = require('crypto');
	    String hash = crypto.createHash('sha256').update(sourceString).digest('base64');
	    */

	}

}
