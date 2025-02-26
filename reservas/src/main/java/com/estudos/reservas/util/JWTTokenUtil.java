package com.estudos.reservas.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenUtil {

    private String secretKey = "my-secret-key";
    private long expirationTime = 1000 * 60 * 60;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            var parser = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build();
            parser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        var parser = Jwts.parser().setSigningKey(secretKey).build();

        var claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
