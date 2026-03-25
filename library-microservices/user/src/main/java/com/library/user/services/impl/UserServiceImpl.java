package com.library.user.services.impl;

import com.library.user.dtos.AccessDTO;
import com.library.user.dtos.TokenDTO;
import com.library.user.dtos.UserDTO;
import com.library.user.mapper.UserMapper;
import com.library.user.models.User;
import com.library.user.repositories.UserRepository;
import com.library.user.services.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JWTService jwtService;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder encoder,JWTService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO addUser(UserDTO user) {
        UserDTO validatedUser = getUserByEmail(user.getUserEmail());
        if (validatedUser != null) {
            return null;
        }
        user.setUserPassword(encoder.encode(user.getUserPassword()));
        return UserMapper.changeToDTO(userRepository.save(UserMapper.changeToEntity(user)));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user==null){
            return null;
        }
        return UserMapper.changeToDTO(user);
    }

    @Override
    public UserDTO getUserById(int userId) {
        return UserMapper.changeToDTO(userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found")));
    }

    @Override
    public TokenDTO login(AccessDTO accessDTO) {
        if (StringUtils.isBlank(accessDTO.getUserEmail()) || StringUtils.isBlank(accessDTO.getUserPassword())) {
            return null;
        }
        UserDTO validatedUser = getUserByEmail(accessDTO.getUserEmail());
        if(validatedUser == null){
            return null;
        }
        if(!encoder.matches(accessDTO.getUserPassword(), validatedUser.getUserPassword())){
            return null;
        }
        return new TokenDTO(jwtService.generateToken(validatedUser.getUserName()), jwtService.generateRefreshToken(validatedUser.getUserName()));
    }

    @Override
    public TokenDTO sendRefreshToken(String refreshToken) {
        if(!jwtService.isRefreshtoken(refreshToken) || !jwtService.isValid(refreshToken)){
            return null;
        }
        return new TokenDTO(jwtService.generateToken(jwtService.extractUserName(refreshToken)),jwtService.generateRefreshToken(jwtService.extractUserName(refreshToken)));
    }
}
