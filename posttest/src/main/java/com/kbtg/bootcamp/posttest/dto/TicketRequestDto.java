package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketRequestDto {

    @NotNull
    @Size(min = 6, max = 6, message = "Please enter lottery number 6 characters")
    private String ticket;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    public TicketRequestDto(){}

    public TicketRequestDto(String ticket, Integer price, Integer amount) {
        if (ticket == null || price == null || amount == null) {
            throw new NullPointerException("ticketId and price and amount cannot be null");
        }
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
    }
}
