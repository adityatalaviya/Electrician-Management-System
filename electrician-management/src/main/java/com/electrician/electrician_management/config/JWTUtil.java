package com.electrician.electrician_management.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secretkey}")
    private String secretkey;

    //1.-> key to convert hmac-sha algorithm
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
    }

    //2. > Generate token
    public String generateToken(Long id,String email,String role){
        return Jwts.builder()
                .subject(email)
                .claim("id",id)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))//10 min valid
                .signWith(getSecretKey())
                .compact();
    }

    //3-> get email from token
    public String getEmailFromToken(String token){
        return  Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    //  Extract role ->admin,electrician,worker
    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public Long getId(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }


}
