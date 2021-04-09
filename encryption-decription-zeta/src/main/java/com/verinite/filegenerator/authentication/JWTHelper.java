package com.verinite.filegenerator.authentication;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JWTHelper {

    public static final String CHALLENGE_INPUT = "challengeInput";

    private final ExecutorService executorService;

    public JWTHelper() {
        this.executorService = Executors.newFixedThreadPool(123, new ThreadFactoryBuilder()
                .setNameFormat("jwt-helper-thread-pool-%d").build());
    }

	public void verifyJWTClaims(String token, PublicKey publicKey, String challengeInput) {
		try {
			SignedJWT jwt = SignedJWT.parse(token);
			boolean res = jwt.verify(new ECDSAVerifier((ECPublicKey) publicKey));
			if (!res)
				throw new RuntimeException("JWT not valid");
			JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
			if (System.currentTimeMillis() > claimsSet.getExpirationTime().toInstant().toEpochMilli()) {
				throw new RuntimeException("Expierd");
			}
			if (!claimsSet.getStringClaim(CHALLENGE_INPUT).equals(challengeInput))
				throw new RuntimeException("Invalid claim");
			
			System.err.println("clam pass");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String verifyJWTClaimsAndReturnChallange(String token, PublicKey public1) {
		
		try {
			SignedJWT jwt = SignedJWT.parse(token);
			boolean res = jwt.verify(new ECDSAVerifier((ECPublicKey) public1));
			if (!res)
				throw new RuntimeException("JWT not valid");
			JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
			if (System.currentTimeMillis() > claimsSet.getExpirationTime().toInstant().toEpochMilli()) {
				throw new RuntimeException("Expierd");
			}
			System.err.println("clam pass");
			System.out.println();
			return claimsSet.getStringClaim(CHALLENGE_INPUT);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}