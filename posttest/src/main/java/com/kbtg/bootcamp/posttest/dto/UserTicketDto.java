package com.kbtg.bootcamp.posttest.dto;

import com.kbtg.bootcamp.posttest.model.LotteryModel;
import com.kbtg.bootcamp.posttest.model.UserModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class UserTicketDto {

    private Integer userTicketId;

    private UserModel user;

    private LotteryModel lottery;

    private String transactionType;

    private Date transactionDate;

    public UserTicketDto() {}
}
