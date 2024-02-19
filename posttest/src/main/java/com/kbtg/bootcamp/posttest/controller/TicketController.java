package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/public/lotteries")
public class TicketController {

    @Autowired
    private UserTicketService userTicketService;

    @GetMapping("{userId}")
    public ResponseDto getLotteries(@PathVariable String userId) {
        return userTicketService.getLotteries(userId);
    }

    @PostMapping("/{userId}/buy/{ticketId}")
    public ResponseDto buyLottery(@RequestBody Map<String, Integer> id, @PathVariable String userId, @PathVariable String ticketId) {
        System.out.println(id);
        return userTicketService.buyLottery(id.get("id"),userId,ticketId);
    }

    @PostMapping("/{userId}/sell/{ticketId}")
    public ResponseDto sellLottery(@RequestBody Map<String, Integer> id, @PathVariable String userId, @PathVariable String ticketId) {
        System.out.println("id : " + id.get("id"));
        return userTicketService.sellLottery(id.get("id"),userId,ticketId);
    }
}
