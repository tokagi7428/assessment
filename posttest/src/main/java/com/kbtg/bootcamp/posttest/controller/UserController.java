package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/public/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String helloUser(){
        return "Hello user!";
    }

    @PostMapping("/createUser")
    public ResponseDto createUser(@Valid @RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

}
