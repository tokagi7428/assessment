package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.service.TicketService;
import com.kbtg.bootcamp.posttest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    private final TicketService ticketService;

    private final UserService userService;

    public AdminController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping()
    public String helloAdmin(){
        return "Hello admin!";
    }

    @PostMapping("/lotteries")
    public ResponseDto createLotteries(@Valid @RequestBody TicketDto ticketDto){
        return ticketService.createLotteries(ticketDto);
    }

    @GetMapping("/lotteries")
    public List<TicketDto> getAllLotteries(){
        return ticketService.getAllLotteries();
    }

    @PatchMapping("/lottery/{id}")
    public ResponseDto editLottery(@Valid @RequestBody TicketDto ticketDto, @PathVariable Integer id){
        return ticketService.editLottery(ticketDto,id);
    }

    @DeleteMapping("/lottery/{id}")
    public ResponseDto deleteLottery(@Valid @RequestBody TicketDto ticketDto, @PathVariable Integer id){
        return ticketService.deleteLottery(ticketDto,id);
    }

    @PatchMapping("/userEdit/{id}")
    public ResponseDto editUser(@Valid @RequestBody UserDto user, @PathVariable String id){
        return userService.editUser(user,id);
    }
}
