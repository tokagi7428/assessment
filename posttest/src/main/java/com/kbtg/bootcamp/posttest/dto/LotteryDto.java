package com.kbtg.bootcamp.posttest.dto;

import com.kbtg.bootcamp.posttest.model.LotteryModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class LotteryDto {

    @NotNull
    @Size(min = 6, max = 6, message = "Please enter lottery number 6 characters")
    private String ticket;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    private String status;

    private Boolean active;

    private Integer id;


}
