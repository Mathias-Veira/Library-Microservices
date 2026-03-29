package com.library.loan.services;

import com.library.loan.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserClient {
    @GetMapping("/{userId}")
    UserDTO findUserById(@PathVariable int userId);
}
