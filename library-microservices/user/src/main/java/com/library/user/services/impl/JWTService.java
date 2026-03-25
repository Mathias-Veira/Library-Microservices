package com.library.user.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 1000*60*60;
    private final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7;
    public String generateToken(String username){
        return Jwts.builder().setSubject(username).claim("type","access").setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(KEY).compact();
    }

    public String generateRefreshToken(String username){
        return Jwts.builder().setSubject(username).claim("type","refresh").setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION)).signWith(KEY).compact();
    }
    public String extractUserName(String token){
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody().getSubject();

    }
    public boolean isValid(String token){
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return  false;
        }
    }

    public boolean isRefreshtoken(String token){
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
            return claims.get("type").equals("refresh");
        }catch (JwtException e){
            return  false;
        }

    }


}
