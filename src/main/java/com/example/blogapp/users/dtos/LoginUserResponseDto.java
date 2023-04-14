package com.example.blogapp.users.dtos;

import lombok.Data;

@Data
public class LoginUserResponseDto {
    private String username;
    private String bio;
    private String imageLink;
    private String loggedInfo = "You are logged in!";
    private String token;
}
