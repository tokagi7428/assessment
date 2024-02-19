package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.TicketModel;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public ResponseDto createLotteries(TicketDto ticketDto) {
        System.out.println(ticketDto.toString());
        Optional<TicketModel> ticketModelOpt = ticketRepository.findByTicketId(ticketDto.getTicketId());
        if(ticketModelOpt.isPresent()) {
            throw new BadRequestException("หมายเลขนี้มีการใช้งานแล้ว");
        }
        TicketModel ticketModel = new TicketModel();
        ticketModel.setTicketId(ticketDto.getTicketId());
        ticketModel.setAmount(ticketDto.getAmount());
        ticketModel.setPrice(ticketDto.getPrice());
        ticketModel.setStatus("ACTIVE");
        ticketModel.setActive(true);
        ticketRepository.save(ticketModel);
        return new ResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ticketModel.getTicketId(),
                "create lottery successfully"
        );
    }

    @Override
    public List<TicketDto> getAllLotteries() {

        Optional<List<TicketModel>> listOptional = ticketRepository.findAllTicketsNotStatus("BUY");

        if(listOptional.isPresent()) {
            List<TicketDto> list = new ArrayList<TicketDto>();
            for(TicketModel model : listOptional.get()) {
                TicketDto ticketDto = new TicketDto();
                ticketDto.setTicketId(model.getTicketId());
                ticketDto.setAmount(model.getAmount());
                ticketDto.setPrice(model.getPrice());
                ticketDto.setId(model.getId());
                ticketDto.setStatus(model.getStatus());
                ticketDto.setActive(model.getActive());
                list.add(ticketDto);
            }
            return list;
        }
        throw new NotFoundException("ไม่มีล็อตเตอรี่ในระบบ กรุณาตรวจสอบข้อมูล");
    }

    @Override
    @Transactional
    public ResponseDto editLottery(TicketDto ticketDto, Integer id) {
        Optional<TicketModel> lotteryModelOpt = ticketRepository.findById(id);
        if(lotteryModelOpt.isPresent()) {
            TicketModel ticketModel = lotteryModelOpt.get();
            ticketModel.setAmount(ticketDto.getAmount());
            ticketModel.setPrice(ticketDto.getPrice());
            if(ticketDto.getStatus() != null){
                ticketModel.setStatus(ticketDto.getStatus());
            }
            if(ticketDto.getActive() != null){
                ticketModel.setActive(ticketDto.getActive());
            }

            ticketRepository.save(ticketModel);
            return new ResponseDto(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    ticketModel,
                    "updated lottery successfully"
            );
        }
        throw new BadRequestException("หมายเลขนี้มีการใช้งานแล้ว");
    }

    @Override
    @Transactional
    public ResponseDto deleteLottery(TicketDto ticketDto, Integer id) {
        Optional<TicketModel> lotteryModelOpt = ticketRepository.findById(id);
        if(lotteryModelOpt.isPresent()) {
            TicketModel ticketModel = lotteryModelOpt.get();
            ticketModel.setStatus("DELETE");
            ticketRepository.save(ticketModel);
            return new ResponseDto(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    null,
                    "deleted this ticket : " + ticketDto.getTicketId() + " successfully"
            );
        }
        throw new BadRequestException("หมายเลขนี้มีการใช้งานแล้ว");
    }

}
