package com.library.user.mapper;

import com.library.user.dtos.UserDTO;
import com.library.user.models.User;

public class UserMapper {
    public static UserDTO changeToDTO(User user){
        return new UserDTO(user.getUserId(),user.getUserName(), user.getUserPassword(), user.getUserEmail());
    }
    public static User changeToEntity(UserDTO user){
        return new User(user.getUserId(), user.getUserName(), user.getUserPassword(), user.getUserEmail());
    }
}
