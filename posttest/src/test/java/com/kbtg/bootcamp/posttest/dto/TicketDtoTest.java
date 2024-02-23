package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class TicketDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("TicketDto is not valid")
    public void ticketDto_isNotValid() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1);
        ticketDto.setTicket("123456");
        ticketDto.setActive(true);
        ticketDto.setPrice(100);
        ticketDto.setAmount(100);
        ticketDto.setStatus("ACTIVE");

        var violations = validator.validate(ticketDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("TicketDto equal values")
    public void ticketDto_EqualValue() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1);
        ticketDto.setTicket("123456");
        ticketDto.setActive(true);
        ticketDto.setPrice(100);
        ticketDto.setAmount(100);
        ticketDto.setStatus("ACTIVE");

        assertThat(ticketDto.getId()).isEqualTo(1);
        assertThat(ticketDto.getTicket()).isEqualTo("123456");
        assertThat(ticketDto.getActive()).isEqualTo(true);
        assertThat(ticketDto.getStatus()).isEqualTo("ACTIVE");
        assertThat(ticketDto.getPrice()).isEqualTo(100);
        assertThat(ticketDto.getAmount()).isEqualTo(100);
    }

    @Test
    @DisplayName("TicketDto is not null")
    public void ticketDto_isNotNull() {
        TicketDto ticketDto = new TicketDto("123456", 100, 80, "ACTIVE", true, 1);

        assertThat(ticketDto).isNotNull();
    }
    @Test
    @DisplayName("ticketDto some field is null")
    public void ticketDto_someFieldIsnull() {
        assertThrows(NullPointerException.class, () -> new TicketDto(null, 100, 80, "ACTIVE", true, 1));
    }

}