package com.kbtg.bootcamp.posttest.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTicketModelTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("userTicketDto is not valid")
    public void userTicketDto_isNotValid() {
        UserTicketModel userTicketModel = new UserTicketModel();
        userTicketModel.setUserId("123456abcd");
        userTicketModel.setTicketId(1);
        userTicketModel.setTransactionType("BUY");
        userTicketModel.setId(1);
        userTicketModel.setTransactionBuyDate(LocalDateTime.now());
        userTicketModel.setTransactionSellDate(LocalDateTime.now());

        var violations = validator.validate(userTicketModel);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("userTicketDto equal values")
    public void userTicketDto_EqualValue() {
        UserTicketModel userTicketModel = new UserTicketModel();
        userTicketModel.setUserId("123456abcd");
        userTicketModel.setTicketId(1);
        userTicketModel.setTransactionType("BUY");
        userTicketModel.setId(1);
        userTicketModel.setTransactionBuyDate(LocalDateTime.now());
        userTicketModel.setTransactionSellDate(LocalDateTime.now());

        assertThat(userTicketModel.getId()).isEqualTo(1);
        assertThat(userTicketModel.getTicketId()).isEqualTo(1);
        assertThat(userTicketModel.getUserId()).isEqualTo("123456abcd");
        assertThat(userTicketModel.getTransactionType()).isEqualTo("BUY");
        assertNotNull(userTicketModel.getTransactionSellDate());
        assertNotNull(userTicketModel.getTransactionBuyDate());
    }

    @Test
    @DisplayName("TicketDto is not null")
    public void ticketDto_isNotNull() {
        UserTicketModel userTicketModel = new UserTicketModel();
        userTicketModel.setUserId("123456abcd");
        userTicketModel.setTicketId(1);
        userTicketModel.setTransactionType("BUY");
        userTicketModel.setId(1);
        userTicketModel.setTransactionBuyDate(LocalDateTime.now());
        userTicketModel.setTransactionSellDate(LocalDateTime.now());

        assertThat(userTicketModel).isNotNull();
    }

}