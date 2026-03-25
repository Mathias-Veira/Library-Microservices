package com.library.user.controllers;

import com.library.user.dtos.*;
import com.library.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<?> addUser(@RequestBody UserDTO userDTO){
        UserDTO validatedUser = userService.addUser(userDTO);
        if(validatedUser == null){
            return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST,"Email already in use"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(validatedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody AccessDTO accessDTO){
        TokenDTO tokenDTO = userService.login(accessDTO);
        if(tokenDTO == null){
            return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST,"Login failed"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tokenDTO,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    ResponseEntity<?> findUserById(@PathVariable int userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping("/refresh")
    ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
        TokenDTO tokenDTO = userService.sendRefreshToken(refreshTokenDTO.getRefreshToken());
        if( tokenDTO == null){
            return new ResponseEntity<>(new ErrorDTO(HttpStatus.UNAUTHORIZED,"This token is not a refresh token"),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(tokenDTO,HttpStatus.OK);
    }
}
