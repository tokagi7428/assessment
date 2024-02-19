package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.ResponseDto;

public interface UserTicketService {
    ResponseDto getLotteries(String userId);

    ResponseDto buyLottery(Integer id, String userId, String ticketId);

    ResponseDto sellLottery(Integer id, String userId, String ticketId);
}
