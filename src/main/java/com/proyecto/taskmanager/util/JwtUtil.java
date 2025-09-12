package com.proyecto.taskmanager.util;

import com.proyecto.taskmanager.usuario.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secretKey;


    @Value("${jwt.expiration}")
    private long expirationTime;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }



    /**Genera un token JWT.
    @param username nombre de usuario
    @return token generado*/
    public String generateToken(Usuario username) {
        Claims claims = Jwts.claims();
        claims.put("usuEmail", username.getEmail());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username.getEmail())
                .setIssuer("ProyectoAPI")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    /**Valida si el token es correcto.*/
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;} catch (JwtException e) {
            return false;}}

    /**

     Obtiene el usuario del token.*/
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();}
}
