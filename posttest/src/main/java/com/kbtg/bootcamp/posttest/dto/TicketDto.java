package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TicketDto {

    @NotNull(message = "ticket is not null")
    @Size(min = 6, max = 6, message = "Please enter lottery number 6 characters")
    private String ticket;

    @NotNull(message = "price is not null")
    private Integer price;

    @NotNull(message = "amount is not null")
    private Integer amount;

    @NotNull(message = "status is not null")
    private String status;

    @NotNull(message = "active is not null")
    private Boolean active;

    @NotNull(message = "id is not null")
    private Integer id;

    public TicketDto(){}

    public TicketDto(String ticket, Integer price, Integer amount, String status, Boolean active, Integer id) {
        if (ticket == null || price == null || amount == null || status == null || active == null) {
            throw new NullPointerException("ticketId and price and amount and status and active cannot be null");
        }
        this.ticket = ticket;
        this.price = price;
        this.amount = amount;
        this.status = status;
        this.active = active;
        this.id = id;
    }
}
