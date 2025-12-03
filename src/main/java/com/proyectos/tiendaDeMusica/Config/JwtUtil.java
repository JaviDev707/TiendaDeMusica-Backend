package com.proyectos.tiendaDeMusica.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;

import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
/**
 * Clase que se encarga del funcionamiento de los JWT
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // Genera el token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername()) // username en el cuerpo
            .setIssuedAt(new Date())               // fecha de creación
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // expiración
            .signWith(getSigningKey(), SignatureAlgorithm.HS256) // firma con clave secreta
            .compact();
    }

    // Extrae username (subject) del token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Valida si el token es correcto para el usuario
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Comprueba si ha expirado
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Obtiene todos los claims del token
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // Convierte el secret en clave para firmar/verificar
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes); // usa HS256
    }

    // Valida el token para saber si el usuario sigue autenticado o para enviarle un nuevo token si el actual está por expirar
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token inválido: " + e.getMessage());
            return false;
        }
    }
    
    // Valida el token con una capa extra de verificación y asegura que el usuario dentro del token coincide con el UserDetails cargado
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}