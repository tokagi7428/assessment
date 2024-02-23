package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.dto.UserRequestDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import com.kbtg.bootcamp.posttest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public Map<String,String> createUser(UserRequestDto userDto) {

        Optional<UserModel> userModel = userRepository.findByUsername(userDto.getUsername());

        if(userModel.isPresent()) {
            throw new BadRequestException("User already exists");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userDto.getPassword());
        UserModel createUser = new UserModel();

        createUser.setPassword(encodedPassword);
        createUser.setUsername(userDto.getUsername());
        createUser.setPermissions(List.of("USER"));
        createUser.setRoles(List.of("USER"));
        createUser.setUserId(UUID.randomUUID().toString().replaceAll("-","").substring(0,10));
        userRepository.save(createUser);

        Map<String, String> mapDataResponse = new HashMap<String, String>();
        mapDataResponse.put("username", userDto.getUsername());
        mapDataResponse.put("userId", createUser.getUserId());

        return mapDataResponse;
    }

    @Override
    @Transactional
    public Map<String,String> editUser(UserDto user, String userId) {
        Optional<UserModel> userModelOpt = userRepository.findByUserId(userId);
        if(userModelOpt.isPresent()) {
            UserModel userModel = userModelOpt.get();
            userModel.setUsername(user.getUsername());
//            userModel.setPassword(user.getPassword());
            userModel.setRoles(user.getRoles());
            userModel.setPermissions(user.getPermissions());
            userRepository.save(userModel);
        }else{
            throw new NotFoundException("User not found");
        }
        Map<String, String> mapDataResponse = new HashMap<String, String>();
        mapDataResponse.put("username", userModelOpt.get().getUsername());
        return mapDataResponse;
    }

}
