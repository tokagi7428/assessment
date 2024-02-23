package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserRequestDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("UserRequestDto is not valid")
    public void userRequestDto_isNotValid() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("user");
        userRequestDto.setPassword("password");

        var violations = validator.validate(userRequestDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("userRequestDto equal values")
    public void userRequestDto_EqualValue() {
        String username = "user";
        String password = "password";

        UserRequestDto userRequestDto = new UserRequestDto(username,password);

        assertThat(userRequestDto.getUsername()).isEqualTo(username);
        assertThat(userRequestDto.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("userRequestDto is not null")
    public void userRequestDto_isNotNull() {
        UserRequestDto userRequestDto = new UserRequestDto();

        assertThat(userRequestDto).isNotNull();
    }

    @Test
    @DisplayName("constructor is null and throw null pointer exception")
    public void constructor_isnull() {
        assertThrows(NullPointerException.class, () -> new UserRequestDto(null, null));
    }
    @Test
    @DisplayName("username is null and throw null pointer exception")
    public void username_nullPointer() {
        assertThrows(NullPointerException.class, () -> new UserRequestDto(null, "password"));
    }

    @Test
    @DisplayName("password is null and throw null pointer exception")
    public void password_nullPointer() {
        assertThrows(NullPointerException.class, () -> new UserRequestDto("username", null));
    }
}