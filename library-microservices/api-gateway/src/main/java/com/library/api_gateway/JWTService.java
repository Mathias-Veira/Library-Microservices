package com.library.api_gateway;

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
