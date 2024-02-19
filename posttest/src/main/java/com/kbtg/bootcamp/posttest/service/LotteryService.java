package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;

import java.util.List;

public interface LotteryService {
    ResponseDto createLotteries(LotteryDto lotteryDto);

    List<LotteryDto> getAllLotteries();

    ResponseDto editLottery(LotteryDto lotteryDto, Integer id);

    ResponseDto deleteLottery(LotteryDto lotteryDto, Integer id);
}
