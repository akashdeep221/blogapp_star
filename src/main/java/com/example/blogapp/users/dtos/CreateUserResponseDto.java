package com.example.blogapp.users.dtos;

import lombok.Data;

@Data
public class CreateUserResponseDto {
    private String username;
    private String email;
    private String bio;
    private String imageLink;
}
