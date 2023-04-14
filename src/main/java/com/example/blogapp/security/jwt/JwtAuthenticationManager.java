package com.example.blogapp.security.jwt;

import com.example.blogapp.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationManager implements AuthenticationManager {
    private JwtService jwtService;
    private UserService userService;

    public JwtAuthenticationManager(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthentication) {
            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;

            var jwtString = jwtAuthentication.getCredentials();
            var email = jwtService.getEmailFromJwt(jwtString);
            // TODO: crypto failure on jwt verification
            // TODO: check if jwt is expired

            var user = userService.findUserByEmail(email);
            jwtAuthentication.setUser(user);
            return jwtAuthentication;
        }
        return null;
    }
}
