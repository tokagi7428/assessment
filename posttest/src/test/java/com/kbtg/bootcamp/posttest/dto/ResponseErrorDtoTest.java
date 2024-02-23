package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResponseErrorDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("ResponseErrorDto is not valid")
    public void userRequestDto_isNotValid() {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto();
        responseErrorDto.setError(null);
        responseErrorDto.setPath(null);
        responseErrorDto.setTimestamp(null);
        responseErrorDto.setMessage(null);

        var violations = validator.validate(responseErrorDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("ResponseErrorDto equal values")
    public void userRequestDto_EqualValue() {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "error not found", "User not found", "/public/user");
        assertNotNull(responseErrorDto.getTimestamp());
        assertThat(responseErrorDto.getError()).isEqualTo("error not found");
        assertThat(responseErrorDto.getMessage()).isEqualTo("User not found");
        assertThat(responseErrorDto.getStatus()).isEqualTo(404);
        assertThat(responseErrorDto.getPath()).isEqualTo("/public/user");
    }

    @Test
    @DisplayName("ResponseErrorDto is not null")
    public void userRequestDto_isNotNull() {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), null, "User not found", "/public/user");

        assertThat(responseErrorDto).isNotNull();
    }

}