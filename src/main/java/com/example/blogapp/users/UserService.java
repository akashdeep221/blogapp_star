package com.example.blogapp.users;

import com.example.blogapp.security.jwt.JwtService;
import com.example.blogapp.users.dtos.CreateUserDto;
import com.example.blogapp.users.dtos.CreateUserResponseDto;
import com.example.blogapp.users.dtos.LoginUserDto;
import com.example.blogapp.users.dtos.LoginUserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public CreateUserResponseDto createUser(CreateUserDto newUser) {
        // Data Validation
//        if (newTask.getDueDate().before(new Date())) {
//            throw new IllegalArgumentException("Due date cannot be in the past");
//        }

        UserEntity user = modelMapper.map(newUser, UserEntity.class);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        var savedUser = userRepository.save(user);
        // OPTION 1: Server side token
        // var token = authTokenService.createToken(savedUser);
        // response.setToken(token);
        // OPTION 2: JWT
        // var token = jwtService.createJwt(savedUser.getUsername());
        return modelMapper.map(savedUser, CreateUserResponseDto.class);
    }
    public LoginUserResponseDto loginUser(LoginUserDto loginUser) {

        UserEntity user = userRepository.findByEmail(loginUser.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        LoginUserResponseDto response = modelMapper.map(user, LoginUserResponseDto.class);
        response.setToken(jwtService.createJwt(user.getEmail()));
        return response;
    }
    public LoginUserResponseDto findUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return modelMapper.map(user, LoginUserResponseDto.class);
    }
    public UserEntity findAuthorByEmail(String email){
        UserEntity user = userRepository.findByEmail(email);

        if (user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return user;
    }
}
