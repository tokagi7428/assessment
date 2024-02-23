package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.TicketResponse;
import com.kbtg.bootcamp.posttest.dto.UserRequestDto;
import com.kbtg.bootcamp.posttest.service.UserService;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserTicketService userTicketService;

    public UserController(UserService userService, UserTicketService userTicketService) {
        this.userService = userService;
        this.userTicketService = userTicketService;
    }

    @GetMapping()
    public String helloUser(){
        return "Hello user!";
    }

    @PostMapping("/createUser")
    public ResponseEntity<Map<String,String>> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.createUser(userRequestDto),HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> buyLottery(@PathVariable("userId") String userId, @PathVariable("ticketId") Integer ticketId) {
        return new ResponseEntity<>(userTicketService.buyLottery(userId,ticketId),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> sellLottery(@PathVariable("userId") String userId, @PathVariable("ticketId") Integer ticketId) {
        return new ResponseEntity<>(userTicketService.sellLottery(userId,ticketId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<TicketResponse> getMyLotteries(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userTicketService.getMyLotteries(userId),HttpStatus.OK);
    }

}
