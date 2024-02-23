package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.dto.UserRequestDto;
import com.kbtg.bootcamp.posttest.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    Optional<UserModel> findByUsername(String username);

    Map<String,String> createUser(UserRequestDto userDto);

    Map<String,String> editUser(UserDto user, String id);
}
