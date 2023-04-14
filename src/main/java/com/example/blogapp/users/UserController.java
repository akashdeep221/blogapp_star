package com.example.blogapp.users;

import com.example.blogapp.users.dtos.CreateUserDto;
import com.example.blogapp.users.dtos.CreateUserResponseDto;
import com.example.blogapp.users.dtos.LoginUserDto;
import com.example.blogapp.users.dtos.LoginUserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserDto createUser) {
        CreateUserResponseDto savedUser = userService.createUser(createUser);

        return
                ResponseEntity
                .created(URI.create("/users/" + savedUser.getUsername()))
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> loginUser(@RequestBody LoginUserDto loginUser) {

        LoginUserResponseDto loggedInUser = userService.loginUser(loginUser);

        return ResponseEntity.ok(loggedInUser);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
}
