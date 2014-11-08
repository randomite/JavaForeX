import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Main {

	public static void main(String[] args){
		Token xPayToken = new Token();

		String token = xPayToken.buildHash(args[0],args[1]);
		
		System.out.println("xpay token is : " + token);
	}

}
