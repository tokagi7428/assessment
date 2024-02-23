package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.TicketRequestDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.TicketModel;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.service.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }


    @Override
    @Transactional
    public Map<String,String> createLotteries(TicketRequestDto ticketRequestDto) {
        Optional<TicketModel> ticketModelOpt = ticketRepository.findByTicket(ticketRequestDto.getTicket());
        if(ticketModelOpt.isPresent()) {
            throw new BadRequestException("Ticket already exists");
        }
        TicketModel ticketModel = new TicketModel();
        ticketModel.setTicket(ticketRequestDto.getTicket());
        ticketModel.setAmount(ticketRequestDto.getAmount());
        ticketModel.setPrice(ticketRequestDto.getPrice());
        ticketModel.setStatus("ACTIVE");
        ticketModel.setActive(true);
        ticketRepository.save(ticketModel);

        Map<String,String> mapUsername = new HashMap<>();
        mapUsername.put("ticket", ticketRequestDto.getTicket());
        return mapUsername;
    }

    @Override
    public List<TicketDto> getAllLotteries() {

        List<TicketModel> listTicket = ticketRepository.findAll();

        if(listTicket.size() > 0) {
            List<TicketDto> list = new ArrayList<TicketDto>();
            for(TicketModel model : listTicket) {
                TicketDto ticketDto = new TicketDto();
                ticketDto.setTicket(model.getTicket());
                ticketDto.setAmount(model.getAmount());
                ticketDto.setPrice(model.getPrice());
                ticketDto.setId(model.getId());
                ticketDto.setStatus(model.getStatus());
                ticketDto.setActive(model.getActive());
                list.add(ticketDto);
            }
            return list;
        }
        throw new NotFoundException("Not found ticket in system. Please check data");
    }

    @Override
    @Transactional
    public Map<String,Object> editLottery(TicketDto ticketDto, Integer id) {
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
            Map<String,Object> mapTicketDto = new HashMap<>();
            mapTicketDto.put("ticket", ticketModel);
            return mapTicketDto;
        }
        throw new NotFoundException("Not found ticket");
    }

    @Override
    @Transactional
    public Map<String,String> deleteLottery(String ticketId, Integer id) {
        Optional<TicketModel> lotteryModelOpt = ticketRepository.findById(id);
        if(lotteryModelOpt.isPresent()) {
            TicketModel ticketModel = lotteryModelOpt.get();
            ticketModel.setStatus("DELETE");
            ticketRepository.save(ticketModel);

            Map<String,String> mapTicketDto = new HashMap<>();
            mapTicketDto.put("ticket", ticketModel.getTicket());
            return mapTicketDto;
        }
        throw new NotFoundException("Not found ticket");
    }

    @Override
    public Map<String,List<String>> getAllLotteriesUser() {
        List<TicketModel> listTicket = ticketRepository.findAll();
        Map<String,List<String>> mapTickets = new HashMap<String,List<String>>();
        if(listTicket.size() > 0) {
            List<String> list = new ArrayList<String>();
            for(TicketModel model : listTicket) {
                list.add(model.getTicket());
            }
            mapTickets.put("tickets", list.stream().sorted().toList());
            return mapTickets;
        }
        throw new NotFoundException("Not found ticket in system. Please check data");
    }

}
