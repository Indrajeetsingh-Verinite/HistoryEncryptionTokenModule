package com.verinite.filegenerator;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nimbusds.jose.JOSEException;
import com.verinite.filegenerator.authentication.GenerateKeyHelper;
import com.verinite.filegenerator.authentication.GenerateToken;
import com.verinite.filegenerator.authentication.JWTHelper;


@SpringBootApplication
public class FileGeneratorApplication {

	public  void tokenExample() throws JOSEException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
		//SpringApplication.run(FileGeneratorApplication.class, args);
		
		KeyPair keys= GenerateKeyHelper.generateKeyPair();
		
		System.out.println("private Key : " + new String(java.util.Base64.getEncoder().encode(keys.getPrivate().getEncoded())));
        System.out.println("public Key : " + new String(java.util.Base64.getEncoder().encode(keys.getPublic().getEncoded())));
        String encodedPublicKey = new String(java.util.Base64.getEncoder().encode(keys.getPublic().getEncoded()));
        String encodedPrivatekey = new String(java.util.Base64.getEncoder().encode(keys.getPrivate().getEncoded()));
        
//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
//        byte[] privatekeyEncoded = java.util.Base64.getDecoder().decode("MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCD3QECD/f+bdqRun1K59kwdUbHa9K2m6rX6uftP5L8tIw==");
//
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privatekeyEncoded);
//        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//        System.err.println(privateKey);
        
//		String s = "{\n"
//				+ "  \"mid\": \"13\"\n"
//				+ "}";
//		GenerateToken tokenObject = new GenerateToken();
//		String token = tokenObject.getToken(s, 30000, keys.getPrivate());
//		System.err.println(token);
//		
		//public key to publicObject
//		KeyFactory keyFactory1 = KeyFactory.getInstance("EC");
//	      byte[] publicKeyEncoded = java.util.Base64.getDecoder().decode(encodedPublicKey);
//
//	      X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyEncoded);
//	      PublicKey publicKey1 = keyFactory1.generatePublic(x509EncodedKeySpec);
		
//		//System.err.println(publicKey1);
//		
//		JWTHelper jwtHelper = new JWTHelper();
//		String clam = jwtHelper.verifyJWTClaimsAndReturnChallange(token,publicKey1);
//		System.err.println(clam);
//		
//		//actual token generation
		System.err.println("\n==============\nActual Token");
		String signedData ="{\r\n"
				   + "        \"transactionAmount\" : {\r\n"
				   + "        \"currency\" : \"INR\",\r\n"
				   + "        \"exponent\" : \"2\",\r\n"
				   + "        \"amount\" : \"1258796541323455454\",\r\n"
				   + "        \"displayAmount\" : \"1258796541323455454\",\r\n"
				   + "        \"currencySymbol\" : \"rs\"\r\n"
				   + "        },\r\n"
				   + "        \"merchantInfo\" : {\r\n"
				   + "        \"mid\" :\"123123\",\r\n"
				   + "        \"country\" : \"Ind\",\r\n"
				   + "        \"url\" :\"\",\r\n"
				   + "        \"merchantName\" : \"amazon\"\r\n"
				   + "        },\r\n"
				   + " \"pansha\":\"223456789012348\",\r\n"
				   + " \"maskedPan\":\"1234XXXXXXXX098\",\r\n"
				   + " \"tenantUniqueVector\":\"tenantUniqueVector\"\r\n"
				   + "    }";
		
		  KeyFactory keyFactory = KeyFactory.getInstance("EC");
	        byte[] privatekeyEncoded = java.util.Base64.getDecoder().decode("MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCD3QECD/f+bdqRun1K59kwdUbHa9K2m6rX6uftP5L8tIw==");

	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privatekeyEncoded);
	        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
	        System.err.println(privateKey);
		GenerateToken tokenObject1 = new GenerateToken();
		String token1 = tokenObject1.getToken(signedData, 300000,privateKey);
		System.err.println(token1);
		
		KeyFactory keyFactory1 = KeyFactory.getInstance("EC");
	      byte[] publicKeyEncoded = java.util.Base64.getDecoder().decode("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEqzkTd/5VQar1T0HlzzVTrHPuPZaIe+QFmd/b1tI78E1MzmTxjQ0WCaQyhJhOFevUV0uRBHck3kSknpLwIX4v3g==");

	      X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyEncoded);
	      PublicKey publicKey1 = keyFactory1.generatePublic(x509EncodedKeySpec);
	      
	      JWTHelper jwtHelper = new JWTHelper();
			String clam = jwtHelper.verifyJWTClaimsAndReturnChallange(token1,publicKey1);
			System.err.println(clam);
	}

}
