package com.library.user.services.impl;

import com.library.user.dtos.AccessDTO;
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

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
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
    public boolean login(AccessDTO accessDTO) {
        if (StringUtils.isBlank(accessDTO.getUserEmail()) || StringUtils.isBlank(accessDTO.getUserPassword())) {
            return false;
        }
        UserDTO validatedUser = getUserByEmail(accessDTO.getUserEmail());
        if(validatedUser == null){
            return false;
        }
        return encoder.matches(accessDTO.getUserPassword(), validatedUser.getUserPassword());
    }
}
