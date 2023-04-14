package com.example.blogapp.security.jwt;


import com.example.blogapp.users.dtos.LoginUserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private String jwtString;
    private LoginUserResponseDto loggedInUser;

    public JwtAuthentication(String jwtString) {
        this.jwtString = jwtString;
    }

    void setUser(LoginUserResponseDto loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: not needed for now, unless we have different resources accessible to different roles
        return null;
    }

    @Override
    public String getCredentials() {
        // This is where we return the string/token that is used for authentication
        return jwtString;
    }

    @Override
    public Object getDetails() {
        // TODO: not needed for now
        return null;
    }

    @Override
    public LoginUserResponseDto getPrincipal() {
        // This is where we return the user/client who is getting authenticated
        return loggedInUser;
    }

    @Override
    public boolean isAuthenticated() {
        return (loggedInUser != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}