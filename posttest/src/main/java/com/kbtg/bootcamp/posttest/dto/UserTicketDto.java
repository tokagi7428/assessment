package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTicketDto {

    @NotNull
    private Integer id;

    @NotNull
    @Size(min = 10,max = 10)
    private String userId;

    @NotNull
    @Size(min = 6,max = 6)
    private String ticket;

    @NotNull
    private String transactionType;

    private LocalDateTime transactionSellDate;

    private LocalDateTime transactionBuyDate;

    public UserTicketDto() {}

    public UserTicketDto(Integer id, String userId, String ticket, String transactionType, LocalDateTime transactionSellDate, LocalDateTime transactionBuyDate) {
        if(id == null || userId == null || ticket == null || transactionType == null){
            throw new NullPointerException("id or ticketId or transactionType cannot be null");
        }

        this.id = id;
        this.userId = userId;
        this.ticket = ticket;
        this.transactionType = transactionType;
        this.transactionSellDate = transactionSellDate;
        this.transactionBuyDate = transactionBuyDate;
    }
}
