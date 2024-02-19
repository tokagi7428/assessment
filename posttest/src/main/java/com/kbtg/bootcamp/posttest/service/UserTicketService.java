package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;

public interface UserTicketService {
    ResponseDto getLotteries(String userId);

    ResponseDto sellLottery(Integer id, String userId, Integer ticketId);

    ResponseDto soldLottery(String id, String userId, Integer ticketId);
}
