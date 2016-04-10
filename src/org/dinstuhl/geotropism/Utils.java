package org.dinstuhl.geotropism;


import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;

import org.apache.commons.codec.binary.Base64;

public class Utils {

	public static String encrypt(String lat, String lng, String message){
		
		String encryptedMessage = new String();

		String plainKey = lat + lng;
		byte[] hashedKey;
		
		try{
			hashedKey = createKey(plainKey, message);

			encryptedMessage = encryptMessage(hashedKey, message);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return encryptedMessage;
		
	}
	
	public static String decrypt(String lat, String lng, String message){
		
		String plainMessage = new String();
		String plainKey = lat + lng;
		byte[] hashedKey;
		
		try{
			// hash the key
			hashedKey = createKey(plainKey, message);
			// pass to the internal decrypt method
			plainMessage = decryptMessage(hashedKey, message);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return plainMessage;
		
	}
	
	private static String decryptMessage(byte[] hashedKey, String message){
		
		String decryptedMessage = new String();

		try{
			  Cipher cipher = Cipher.getInstance("AES");
			  SecretKey secKey = new SecretKeySpec(hashedKey, "AES");

			  cipher.init(Cipher.DECRYPT_MODE, secKey);
			  byte[] newData = cipher.doFinal(Base64.decodeBase64(message.getBytes()));
			  decryptedMessage = new String(newData);
		}
		catch(javax.crypto.BadPaddingException bpe){
			decryptedMessage = "";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return decryptedMessage;
		
	}
	
	private static String encryptMessage(byte[] hashedKey, String message){
		
		String encryptedMessage = new String();

		try{
		  SecretKey secKey = new SecretKeySpec(hashedKey,"AES");

		  Cipher cipher = Cipher.getInstance("AES");
		  
		  cipher.init(Cipher.ENCRYPT_MODE, secKey);
		  byte[] newData = cipher.doFinal(message.getBytes());
		  
		  encryptedMessage = Base64.encodeBase64String(newData);
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
		return encryptedMessage;
		
	}
	
	
	private static byte[] createKey(String plainKey, String message){
		
		
		byte[] binaryKey = null;
		MessageDigest messageDigest;
		//
		//Create a MD5 hash of the lng + the lat
		try{
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(plainKey.getBytes("UTF-8"));
			binaryKey = messageDigest.digest();
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		
		return binaryKey;
		
	}
	
}
