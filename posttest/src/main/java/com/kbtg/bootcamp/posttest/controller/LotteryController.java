package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/public/lotteries")
public class LotteryController {

    @Autowired
    private UserTicketService userTicketService;

    @GetMapping("{userId}")
    public ResponseDto getLotteries(@PathVariable String userId) {
        return userTicketService.getLotteries(userId);
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseDto sellLottery(@RequestBody Map<String, Integer> id, @PathVariable String userId, @PathVariable Integer ticketId) {
        System.out.println(id);
        return userTicketService.sellLottery(id.get("id"),userId,ticketId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseDto soldLottery(@RequestBody Map<String, String> id, @PathVariable String userId, @PathVariable Integer ticketId) {
        System.out.println("id : " + id.get("id"));
        return userTicketService.soldLottery(id.get("id"),userId,ticketId);
    }
}
