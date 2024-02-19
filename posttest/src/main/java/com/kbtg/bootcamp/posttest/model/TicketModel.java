package com.kbtg.bootcamp.posttest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity()
@Table(name = "tickets")
@Getter
@Setter
public class TicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @NotBlank
    @Column(name = "ticket_id")
    @Size(min = 6, max = 6, message = "Please enter lottery id 6 characters")
    private String ticketId;

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

    public TicketModel(){}

}
