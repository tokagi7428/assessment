package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.TicketResponse;

import java.util.List;
import java.util.Map;

public interface UserTicketService {
    Map<String, List<String>> getLotteries(String userId);

    Map<String,String> buyLottery(String userId, Integer ticketId);

    Map<String,String> sellLottery(String userId, Integer ticketId);

    TicketResponse getMyLotteries(String userId);
}
