package com.example.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogappApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // todo: exception handling - when certain required body params are null - only 403 forbidden in response,
    // todo: - when certain unique body params are non-unique, 403 forbidden in response,
    // todo: while logging in, when the reqd body is not there, 403 forbidden
    // delete v4
    // postgre db - test to find whether it is storing encoded passwords or not
    // authentication
    // testing
    // docker and deployment
    //
}
