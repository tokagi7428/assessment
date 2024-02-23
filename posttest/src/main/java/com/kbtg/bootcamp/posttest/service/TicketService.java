package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.TicketRequestDto;

import java.util.List;
import java.util.Map;

public interface TicketService {
    Map<String,String> createLotteries(TicketRequestDto ticketRequestDto);

    List<TicketDto> getAllLotteries();

    Map<String,Object> editLottery(TicketDto ticketDto, Integer id);

    Map<String,String> deleteLottery(String ticketId, Integer id);

    Map<String,List<String>> getAllLotteriesUser();
}
