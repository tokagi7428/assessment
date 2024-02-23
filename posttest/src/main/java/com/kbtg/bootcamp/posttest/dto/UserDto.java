package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private List<String> roles;
    @NotNull
    private List<String> permissions;
    @NotNull
    @Size(min = 10, max = 10, message = "Please enter user_id 10 characters")
    private String userId;
    @NotNull
    private Integer id;

    public UserDto(){}

    public UserDto(String username, String password, List<String> roles, List<String> permissions, String userId, Integer id) {
        if(username == null || password == null || roles == null || permissions == null || id == null){
            throw new NullPointerException("username or password or roles or permissions or id cannot be null");
        }
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.userId = userId;
        this.id = id;
    }
}
