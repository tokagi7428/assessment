package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
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

    private List<String> roles;

    private List<String> permissions;

    private Integer id;

    public UserDto(){}

}
