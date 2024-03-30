package com.example.echowprojectsapp.Utilidades.Token;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtDecoder {

    private static final byte[] KEY = "prograMovil".getBytes(StandardCharsets.UTF_8);

    public static String decodeJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(KEY))
                    .build()
                    .parseSignedClaims(jwt)
                    .getBody();

            Map<String, Object> data = (Map<String, Object>) claims.get("data");
            String id = (String) data.get("id");

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle decoding errors as needed
        }
    }
}
