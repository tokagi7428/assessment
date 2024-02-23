package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotNull(message = "username can not be null")
    private String username;

    @NotNull(message = "password can not be null")
    private String password;

    public UserRequestDto(){}

    public UserRequestDto(String username, String password) {
        if (username == null || password == null) {
            throw new NullPointerException("Username and password cannot be null");
        }
        this.username = username;
        this.password = password;
    }
}
