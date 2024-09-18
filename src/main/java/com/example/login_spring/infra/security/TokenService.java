package com.example.login_spring.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.login_spring.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private  String secret;

    public  String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("login-spring")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.getExpirationTime())
                    .sign(algorithm);

            return token;

        }catch (JWTCreationException exception){
            throw  new RuntimeException("Error creating token");
        }
    }

    public String validateToken(String tokem){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("login-spring")
                    .build()
                    .verify(tokem)
                    .getSubject();

        }catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
