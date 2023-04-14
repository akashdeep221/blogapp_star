package com.example.blogapp.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    public static final String SECRET = "f8429fh02j80f4j4289fh4thdjfoeuf94n2b6kn42";
    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String createJwt(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Cannot create JWT with blank email");
        }

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public String getEmailFromJwt(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }
}
