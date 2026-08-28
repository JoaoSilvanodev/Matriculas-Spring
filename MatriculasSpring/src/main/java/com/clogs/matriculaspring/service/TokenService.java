package com.clogs.matriculaspring.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.clogs.matriculaspring.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final String secret;
    private final Algorithm algorithm;

    public TokenService(@Value("${api.security.token.secret:minha-chave-secreta-super-segura-123456}") String secret) {
        this.secret = secret;
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(User user) {

        try {
            return JWT.create()
                    .withIssuer("matriculas-spring-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(this.algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating Token", exception);
        }
    }

    public String validateToken(String token) {
         try {
             return JWT.require(this.algorithm)
                     .withIssuer("matriculas-spring-api")
                     .build()
                     .verify(token)
                     .getSubject();    // devolve o email
         } catch (JWTCreationException exception) {
             return "";
         }
    }

    public Instant generateExpirationDate() {
        return Instant.now().plusSeconds(86400); // 24 hours
    }
}
