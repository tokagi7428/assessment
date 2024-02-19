package com.kbtg.bootcamp.posttest.dto;

import com.kbtg.bootcamp.posttest.model.TicketModel;
import com.kbtg.bootcamp.posttest.model.UserModel;
import lombok.Data;

import java.util.Date;

@Data
public class UserTicketDto {

    private Integer userTicketId;

    private UserModel user;

    private TicketModel lottery;

    private String transactionType;

    private Date transactionSellDate;

    private Date transactionBuyDate;

    public UserTicketDto() {}
}
