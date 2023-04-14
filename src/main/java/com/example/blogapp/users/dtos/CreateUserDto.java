package com.example.blogapp.users.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserDto {
    @NonNull
    String username;
    @NonNull
    String email;
    @NonNull
    String password;
    String bio;
    String imageLink;
}
