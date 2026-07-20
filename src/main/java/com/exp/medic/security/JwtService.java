package com.exp.medic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public CurrentUser parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String id = claims.get("id", String.class);
        String rol = claims.get("rol", String.class);
        String nombreCompleto = claims.get("nombreCompleto", String.class);
        String email = claims.getSubject();

        if (id == null || rol == null) {
            throw new JwtException("El token no trae los claims esperados (id/rol)");
        }

        return new CurrentUser(id, email, nombreCompleto, rol);
    }
}
