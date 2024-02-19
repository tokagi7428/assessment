package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @Size(min = 10,max = 10)
    private String userId;

    @Column(name = "ticket_id")
    @Size(min = 6,max = 6)
    private String ticketId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_sell_date")
    private LocalDateTime transactionSellDate;

    @CreationTimestamp
    @Column(name = "transaction_buy_date")
    private LocalDateTime transactionBuyDate;

    public UserTicketModel() {}

}
