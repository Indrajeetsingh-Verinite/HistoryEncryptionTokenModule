package com.verinite.filegenerator.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.nimbusds.jose.JOSEException;
import com.verinite.filegenerator.authentication.ConvertKeys;
import com.verinite.filegenerator.authentication.GenerateToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class GenerateTokenController {

    @Value("${authtoken.privatekey}")
	private String privateKey ;

    ConvertKeys convertKeys = new ConvertKeys();

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/generate")
    public String generate(@RequestBody String signedData) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException{
        GenerateToken tokenObject = new GenerateToken();
		return tokenObject.getToken(signedData, 30000,convertKeys.stringToPrivateKey(privateKey)); 
    }

    @PostMapping("/saveHistory")
    public Object saveHistory(@RequestBody String signedData) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException{
        GenerateToken tokenObject = new GenerateToken();
		 String token = tokenObject.getToken(signedData, 30000,convertKeys.stringToPrivateKey(privateKey)); 
         return webClientBuilder.build()
                                    .post()
                                    .uri("http://localhost:8086/history")
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .body(Mono.just(token), String.class)
                                    .retrieve()
                                    .bodyToMono(Object.class)
                                    .block();
    }

    @PatchMapping("/updateHistory/{historyId}")
    public Object updateHistory(@RequestBody String signedData,@RequestParam Integer historyId) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException{
        GenerateToken tokenObject = new GenerateToken();
		String token = tokenObject.getToken(signedData, 30000,convertKeys.stringToPrivateKey(privateKey)); 

         return webClientBuilder.build()
                                    .patch()
                                    .uri("http://localhost:8086/history/"+historyId)
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .body(Mono.just(token), String.class)
                                    .retrieve()
                                    .bodyToMono(Object.class)
                                    .block();
    }
}