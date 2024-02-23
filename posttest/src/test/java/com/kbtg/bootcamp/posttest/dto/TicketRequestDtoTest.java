package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TicketRequestDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("TicketDto is not valid")
    public void ticketDto_isNotValid() {
        TicketRequestDto ticketDto = new TicketRequestDto();
        ticketDto.setTicket("123456");
        ticketDto.setPrice(100);
        ticketDto.setAmount(100);

        var violations = validator.validate(ticketDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("TicketDto equal values")
    public void ticketDto_EqualValue() {
        TicketRequestDto ticketRequestDto = new TicketRequestDto();
        ticketRequestDto.setTicket("123456");
        ticketRequestDto.setPrice(100);
        ticketRequestDto.setAmount(100);

        assertThat(ticketRequestDto.getTicket()).isEqualTo("123456");
        assertThat(ticketRequestDto.getPrice()).isEqualTo(100);
        assertThat(ticketRequestDto.getAmount()).isEqualTo(100);
    }

    @Test
    @DisplayName("TicketDto is not null")
    public void ticketDto_isNotNull() {
        TicketRequestDto ticketRequestDto = new TicketRequestDto("123456", 100, 80);

        assertThat(ticketRequestDto).isNotNull();
    }
    @Test
    @DisplayName("ticketDto some field is null")
    public void ticketDto_someFieldIsnull() {
        assertThrows(NullPointerException.class, () -> new TicketRequestDto(null, 100, 80));
    }

}