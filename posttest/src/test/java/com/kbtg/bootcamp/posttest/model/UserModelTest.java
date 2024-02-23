package com.kbtg.bootcamp.posttest.model;

import com.kbtg.bootcamp.posttest.dto.UserDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("UserDto is not valid")
    public void userRequestDto_isNotValid() {
        UserModel userModel = new UserModel("user","password");
        userModel.setId(1);
        userModel.setRoles(List.of("USER"));
        userModel.setPermissions(List.of("USER"));
        userModel.setUserId("abcde12345");
        var violations = validator.validate(userModel);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("userRequestDto equal values")
    public void userRequestDto_EqualValue() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setRoles(List.of("USER"));
        userModel.setPermissions(List.of("USER"));
        userModel.setUserId("abcde12345");
        userModel.setUsername("user");
        userModel.setPassword("password");

        assertThat(userModel.getUsername()).isEqualTo("user");
        assertThat(userModel.getPassword()).isEqualTo("password");
        assertThat(userModel.getId()).isEqualTo(1);
        assertNotNull(userModel.getAuthorities());
        assertThat(userModel.getUserId()).isEqualTo("abcde12345");

        assertThat(userModel.isAccountNonExpired()).isEqualTo(true);
        assertThat(userModel.isAccountNonLocked()).isEqualTo(true);
        assertThat(userModel.isCredentialsNonExpired()).isEqualTo(true);
        assertThat(userModel.isEnabled()).isEqualTo(true);
    }

    @Test
    @DisplayName("userRequestDto is not null")
    public void userRequestDto_isNotNull() {
        UserDto userDto = new UserDto();

        assertThat(userDto).isNotNull();
    }
}