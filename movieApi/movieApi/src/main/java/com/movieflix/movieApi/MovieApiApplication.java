package com.movieflix.movieApi;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;

@SpringBootApplication
public class MovieApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieApiApplication.class, args);
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64Key = Encoders.BASE64.encode(key.getEncoded());
		System.out.println("Generated Key: " + base64Key);

	}

}
