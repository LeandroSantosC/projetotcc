package br.com.matraca.projetotcc.config;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${dev.mode}")
    private boolean devMode;
    
    public String generateToken(String login) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            String token = JWT.create()
                    .withIssuer("Matraca")
                    .withSubject(login)
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public ResponseCookie generateCookie(String token, Duration duration) {
        return ResponseCookie.from("JWT_TOKEN", token)
                .httpOnly(true)
                .secure(devMode ? false : true)
                .sameSite(devMode ? "Lax" : "None")
                .path("/")
                .maxAge(duration)
                .build();
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }


    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            return JWT.require(algorithm)
                    .withIssuer("Matraca")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido ou expirado", e);
        }
    }

}
