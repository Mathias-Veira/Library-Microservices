package com.library.user.services;

import com.library.user.dtos.AccessDTO;
import com.library.user.dtos.UserDTO;

public interface UserService {
   UserDTO addUser(UserDTO user);
   UserDTO getUserByEmail(String email);
   UserDTO getUserById(int userId);
   boolean login(AccessDTO user);
}
