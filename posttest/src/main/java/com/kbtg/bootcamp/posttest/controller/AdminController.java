package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.service.AdminService;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import com.kbtg.bootcamp.posttest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;

    private final UserService userService;

    public AdminController(UserService userService,LotteryService lotteryService) {
        this.userService = userService;
        this.lotteryService = lotteryService;
    }

    @GetMapping()
    public String helloAdmin(){
        return "Hello admin!";
    }

    @PostMapping("/lotteries")
    public ResponseDto createLotteries(@Valid @RequestBody LotteryDto lotteryDto){
        return lotteryService.createLotteries(lotteryDto);
    }

    @GetMapping("/lotteries")
    public List<LotteryDto> getAllLotteries(){
        return lotteryService.getAllLotteries();
    }

    @PatchMapping("/lottery/{id}")
    public ResponseDto editLottery(@Valid @RequestBody LotteryDto lotteryDto, @PathVariable Integer id){
        return lotteryService.editLottery(lotteryDto,id);
    }

    @DeleteMapping("/lottery/{id}")
    public ResponseDto deleteLottery(@Valid @RequestBody LotteryDto lotteryDto, @PathVariable Integer id){
        return lotteryService.deleteLottery(lotteryDto,id);
    }

    @PatchMapping("/userEdit/{id}")
    public ResponseDto editUser(@Valid @RequestBody UserDto user, @PathVariable Integer id){
        return userService.editUser(user,id);
    }
}
