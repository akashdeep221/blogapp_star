package com.example.blogapp.users.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginUserDto {

    @NonNull
    String email;
    @NonNull
    String password;

}
