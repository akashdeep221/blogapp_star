package com.example.blogapp.security;

import com.example.blogapp.security.jwt.JwtAuthenticationFilter;
import com.example.blogapp.security.jwt.JwtAuthenticationManager;
import com.example.blogapp.security.jwt.JwtService;
import com.example.blogapp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public AppSecurityConfig(
            JwtService jwtService,
            UserService usersService
    ) {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(
                new JwtAuthenticationManager(
                        jwtService, usersService
                )
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST,"/users", "/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/articles", "/articles/{article-slug}",
                                "/articles/{article-slug}/comments").permitAll()
                        .requestMatchers(HttpMethod.POST, "/articles").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/articles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/articles/comments").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
