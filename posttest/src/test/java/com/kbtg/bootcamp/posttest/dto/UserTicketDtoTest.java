package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTicketDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("userTicketDto is not valid")
    public void userTicketDto_isNotValid() {
        UserTicketDto userTicketDto = new UserTicketDto();
        userTicketDto.setUserId("123456abcd");
        userTicketDto.setTicket("123456");
        userTicketDto.setTransactionType("BUY");
        userTicketDto.setId(1);
        userTicketDto.setTransactionBuyDate(null);
        userTicketDto.setTransactionSellDate(null);

        var violations = validator.validate(userTicketDto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("userTicketDto equal values")
    public void userTicketDto_EqualValue() {
        UserTicketDto userTicketDto = new UserTicketDto(1, "123456abcd", "123456", "BUY", null, null);

        assertThat(userTicketDto.getId()).isEqualTo(1);
        assertThat(userTicketDto.getTicket()).isEqualTo("123456");
        assertThat(userTicketDto.getUserId()).isEqualTo("123456abcd");
        assertThat(userTicketDto.getTransactionType()).isEqualTo("BUY");
        assertNull(userTicketDto.getTransactionSellDate());
        assertNull(userTicketDto.getTransactionBuyDate());
    }

    @Test
    @DisplayName("TicketDto is not null")
    public void ticketDto_isNotNull() {
        UserTicketDto userTicketDto = new UserTicketDto(1, "abcdef1234", "123456", "BUY", null, null);

        assertThat(userTicketDto).isNotNull();
    }
    @Test
    @DisplayName("ticketDto some field is null")
    public void ticketDto_someFieldIsnull() {
        assertThrows(NullPointerException.class, () -> new UserTicketDto(1, null, "123456", "BUY", null, null));
    }
}