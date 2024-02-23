package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.service.TicketService;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/public/lotteries")
public class TicketController {

    private final UserTicketService userTicketService;

    private final TicketService ticketService;

    public TicketController(UserTicketService userTicketService, TicketService ticketService) {
        this.userTicketService = userTicketService;
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket")
    public String getTickets() {
        return "public ticket!";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, List<String>>> getLotteries(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userTicketService.getLotteries(userId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String,List<String>>> getAllLotteries(){
        return new ResponseEntity<>(ticketService.getAllLotteriesUser(),HttpStatus.OK);
    }

}
