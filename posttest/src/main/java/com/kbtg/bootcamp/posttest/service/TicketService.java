package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;

import java.util.List;

public interface TicketService {
    ResponseDto createLotteries(TicketDto ticketDto);

    List<TicketDto> getAllLotteries();

    ResponseDto editLottery(TicketDto ticketDto, Integer id);

    ResponseDto deleteLottery(TicketDto ticketDto, Integer id);
}
