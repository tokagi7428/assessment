package com.kbtg.bootcamp.posttest.model;

import com.kbtg.bootcamp.posttest.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "user_ticket")
@Getter
@Setter
public class UserTicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "lottery_id")
    private Integer lotteryId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_date")
    @CreationTimestamp
    private Date transactionDate;

    public UserTicketModel() {}

}
