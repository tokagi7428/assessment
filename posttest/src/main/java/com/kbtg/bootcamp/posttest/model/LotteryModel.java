package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity()
@Table(name = "lottery")
@Getter
@Setter
public class LotteryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @NotBlank
    @Column(name = "lottery_number")
    @Size(min = 6, max = 6, message = "Please enter lottery number 6 characters")
    private String lotteryNumber;

    @NotNull
    @Column
    private Integer amount;

    @NotNull
    @Column
    private Integer price;

    @Column
    private Boolean active;

    @Column
    private String status;

    public LotteryModel(){}

}
