package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.TicketRequestDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.service.TicketService;
import com.kbtg.bootcamp.posttest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String,String>> createLotteries(@Valid @RequestBody TicketRequestDto ticketRequestDto){
        return new ResponseEntity<>(ticketService.createLotteries(ticketRequestDto),HttpStatus.CREATED);
    }

    @GetMapping("/lotteries")
    public ResponseEntity<List<TicketDto>> getAllLotteries(){
        return new ResponseEntity<>(ticketService.getAllLotteries(),HttpStatus.OK);
    }

//    @PatchMapping("/lottery/{id}")
//    public ResponseEntity<Map<String,Object>> editLottery(@Valid @RequestBody TicketDto ticketDto, @PathVariable("id") Integer id){
//        return new ResponseEntity<>(ticketService.editLottery(ticketDto,id),HttpStatus.OK);
//    }

//    @DeleteMapping("/lottery/{id}")
//    public ResponseEntity<Map<String,String>> deleteLottery(@PathVariable("id") Integer id){
//        return new ResponseEntity<>(ticketService.deleteLottery(requestBody.get("ticketId"),id),HttpStatus.OK);
//    }

    @PatchMapping("/userEdit/{id}")
    public ResponseEntity<Map<String,String>> editUser(@Valid @RequestBody UserDto user, @PathVariable("id") String id){
        return new ResponseEntity<>(userService.editUser(user,id),HttpStatus.OK);
    }
}
