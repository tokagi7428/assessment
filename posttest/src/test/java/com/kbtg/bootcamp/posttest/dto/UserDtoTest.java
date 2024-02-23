package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("UserDto is not valid")
    public void userRequestDto_isNotValid() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setRoles(List.of("USER"));
        userDto.setPermissions(List.of("USER"));
        userDto.setUserId("abcde12345");
        userDto.setUsername("user");
        userDto.setPassword("password");

        var violations = validator.validate(userDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("userRequestDto equal values")
    public void userRequestDto_EqualValue() {
        String username = "user";
        String password = "password";
        int id = 1;
        String userId = "abcde12345";
        List<String> roles = List.of("USER");
        List<String> permissions = List.of("USER");

        UserDto userDto = new UserDto(username, password, roles, permissions, userId, id);

        assertThat(userDto.getUsername()).isEqualTo(username);
        assertThat(userDto.getPassword()).isEqualTo(password);
        assertThat(userDto.getRoles()).isEqualTo(roles);
        assertThat(userDto.getPermissions()).isEqualTo(permissions);
        assertThat(userDto.getId()).isEqualTo(id);
        assertThat(userDto.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("userRequestDto is not null")
    public void userRequestDto_isNotNull() {
        UserDto userDto = new UserDto();

        assertThat(userDto).isNotNull();
    }

    @Test
    @DisplayName("constructor is null and throw null pointer exception")
    public void constructor_isnull() {
        String username = null;
        String password = "password";
        int id = 1;
        String userId = "abcde12345";
        List<String> roles = List.of("USER");
        List<String> permissions = List.of("USER");

        assertThrows(NullPointerException.class, () -> new UserDto(username, password, roles, permissions, userId, id));
    }

}