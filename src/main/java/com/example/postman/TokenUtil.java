package com.example.postman;

import java.security.Key;
import java.util.Date;


import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtil {
//	private Set<String> validTokens=new HashSet<>);
//	public String generateToken(String username) {
//		String token=username+"_token";
//		validTokens.add(token);
//		return token;
//	}
//	public String validateToken(String token) {
//	    if (token != null && token.endsWith("_token")) {
//	        return token.replace("_token", "");
//	    }
//	    return null;
//	}
	private final String SECRET="mysecretkeymysecretkeymysecretkey";
	private final Key key=Keys.hmacShaKeyFor(SECRET.getBytes());
	public String generateToken(String username,String role) {
		return Jwts.builder()
				.setSubject(username)
		        .claim("role", role)
		        .setIssuedAt(new Date())
		        .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
		        .signWith(key)
		        .compact();
	}
	public Claims validateTokens(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		}
		catch(Exception e) {
			return null;
		}
	}
	}

