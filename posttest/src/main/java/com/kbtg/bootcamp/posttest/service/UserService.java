package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    Optional<UserModel> findByUsername(String username);

    ResponseDto createUser(UserDto userDto);

    ResponseDto editUser(UserDto user, Integer id);
}
