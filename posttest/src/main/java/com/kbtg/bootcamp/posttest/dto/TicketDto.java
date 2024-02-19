package com.kbtg.bootcamp.posttest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TicketDto {

    @NotNull
    @Size(min = 6, max = 6, message = "Please enter lottery number 6 characters")
    private String ticketId;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    private String status;

    private Boolean active;

    private Integer id;


}
