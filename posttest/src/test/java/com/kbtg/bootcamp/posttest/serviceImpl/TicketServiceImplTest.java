package com.kbtg.bootcamp.posttest.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.TicketRequestDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.TicketModel;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @InjectMocks
    TicketServiceImpl ticketService;

    @Mock
    TicketRepository ticketRepository;

    @Test
    @DisplayName("test for create a new user")
    void createLotteries() {
        TicketRequestDto ticketDto = new TicketRequestDto();
        ticketDto.setTicket("000001");
        ticketDto.setAmount(100);
        ticketDto.setPrice(80);
        lenient().when(ticketRepository.findByTicket(anyString())).thenReturn(Optional.empty());

        Map<String,String> mapUsername = ticketService.createLotteries(ticketDto);

        assertEquals("000001",mapUsername.get("ticket"));
        assertNotNull(ticketService.createLotteries(ticketDto));
    }

    @Test
    @DisplayName("create a user and throw exception BadRequest")
    void createLotteries_BadRequest() {
        TicketRequestDto ticketDto = new TicketRequestDto();
        ticketDto.setTicket("000001");
        ticketDto.setAmount(100);
        ticketDto.setPrice(80);

        TicketModel ticketModel = new TicketModel();
        ticketModel.setTicket("000001");
        ticketModel.setAmount(100);
        ticketModel.setPrice(80);
        ticketModel.setStatus("ACTIVE");
        ticketModel.setActive(true);
        lenient().when(ticketRepository.findByTicket(anyString())).thenReturn(Optional.of(ticketModel));

        var exception = assertThrows(BadRequestException.class, () -> ticketService.createLotteries(ticketDto));
        assertEquals("Ticket already exists",exception.getMessage());
    }

    @Test
    @DisplayName("create a user and throw exception NotFoundException")
    void getAllLotteries_notFound() {

        lenient().when(ticketRepository.findByTicket(anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> ticketService.getAllLotteries());
        assertEquals("Not found ticket in system. Please check data",exception.getMessage());
    }

    @Test
    @DisplayName("when ticketService.editLottery() should be return ticket")
    void editLottery() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicket("000001");
        ticketDto.setAmount(100);
        ticketDto.setPrice(80);
        ticketDto.setStatus("ACTIVE");
        ticketDto.setActive(true);
        ticketDto.setId(1);

        TicketModel ticketModel1 = new TicketModel();
        ticketModel1.setId(1);
        ticketModel1.setTicket("000001");
        ticketModel1.setAmount(200);
        ticketModel1.setPrice(100);
        ticketModel1.setStatus("ACTIVE");
        ticketModel1.setActive(true);

        when(ticketRepository.findById(anyInt())).thenReturn(Optional.of(ticketModel1));

        Map<String,Object> mapTicketDto = ticketService.editLottery(ticketDto,1);
        assertNotNull(mapTicketDto);
    }

    @Test
    @DisplayName("edit a user and throw exception NotFoundException")
    void editLotterys_notFound() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicket("000001");
        ticketDto.setAmount(100);
        ticketDto.setPrice(80);
        ticketDto.setStatus("ACTIVE");
        ticketDto.setActive(true);
        ticketDto.setId(1);

        TicketRepository ticketRepository = mock(TicketRepository.class);
        lenient().when(ticketRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> ticketService.editLottery(ticketDto,anyInt()));
        assertEquals("Not found ticket",exception.getMessage());
    }

    @Test
    @DisplayName("when ticketService.deleteLottery() should be return ticket")
    void deleteLottery() {
        TicketModel ticketModel = new TicketModel();
        ticketModel.setId(1);
        ticketModel.setTicket("000001");
        ticketModel.setAmount(200);
        ticketModel.setPrice(100);
        ticketModel.setStatus("ACTIVE");
        ticketModel.setActive(true);

        Map<String,String> mapTicketId = new HashMap<>();
        mapTicketId.put("ticketId", "000001");

        Map<String,String> mapTicketDto = new HashMap<>();
        mapTicketDto.put("ticket", mapTicketId.get("ticketId"));

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticketModel));

        Map<String,String> result = ticketService.deleteLottery("000001",1);
        assertNotNull(result);
        assertEquals("000001",result.get("ticket"));
    }

    @Test
    @DisplayName("delete a user and throw exception NotFoundException")
    void deleteLottery_notFound() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        lenient().when(ticketRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> ticketService.deleteLottery("000001",anyInt()));
        assertEquals("Not found ticket",exception.getMessage());
    }

    @Test
    @DisplayName("getAllLotteriesUser_Success should be return tickets")
    void getAllLotteriesUser_Success() {
        TicketModel ticketModel1 = new TicketModel();
        ticketModel1.setId(1);
        ticketModel1.setTicket("000001");

        TicketModel ticketModel2 = new TicketModel();
        ticketModel2.setId(2);
        ticketModel2.setTicket("000002");

        List<TicketModel> ticketList = List.of(ticketModel1, ticketModel2);

        lenient().when(ticketRepository.findAll()).thenReturn(ticketList);

        Map<String, List<String>> result = ticketService.getAllLotteriesUser();

        assertEquals(2, result.get("tickets").size());
        assertEquals("000001", result.get("tickets").get(0));
        assertEquals("000002", result.get("tickets").get(1));
    }

    @Test
    @DisplayName("getAllLotteriesUser_Success should be return error NotFoundException")
    void getAllLotteriesUser_NotFound() {
        lenient().when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        var exception = assertThrows(NotFoundException.class, () -> ticketService.getAllLotteriesUser());
        assertEquals("Not found ticket in system. Please check data",exception.getMessage());
    }

    @Test
    @DisplayName("getAllLotteries_Successful should be return list of tickets")
    void getAllLotteries_Successful() {
        TicketModel ticketModel1 = new TicketModel();
        ticketModel1.setId(1);
        ticketModel1.setTicket("000001");
        ticketModel1.setAmount(10);
        ticketModel1.setPrice(100);
        ticketModel1.setStatus("ACTIVE");
        ticketModel1.setActive(true);

        TicketModel ticketModel2 = new TicketModel();
        ticketModel2.setId(2);
        ticketModel2.setTicket("000002");
        ticketModel2.setAmount(20);
        ticketModel2.setPrice(200);
        ticketModel2.setStatus("INACTIVE");
        ticketModel2.setActive(false);

        List<TicketModel> ticketList = List.of(ticketModel1, ticketModel2);
        lenient().when(ticketRepository.findAll()).thenReturn(ticketList);

        List<TicketDto> result = ticketService.getAllLotteries();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("000001", result.get(0).getTicket());
        assertEquals(10, result.get(0).getAmount());
        assertEquals(100, result.get(0).getPrice());
        assertEquals("ACTIVE", result.get(0).getStatus());
        assertEquals(true, result.get(0).getActive());

        assertEquals(2, result.get(1).getId());
        assertEquals("000002", result.get(1).getTicket());
        assertEquals(20, result.get(1).getAmount());
        assertEquals(200, result.get(1).getPrice());
        assertEquals("INACTIVE", result.get(1).getStatus());
        assertEquals(false, result.get(1).getActive());
    }

    @Test
    @DisplayName("getAllLotteries_Successful should be return NotFoundException")
    void getAllLotteries_NotFound() {
        lenient().when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        var exception = assertThrows(NotFoundException.class, () -> ticketService.getAllLotteries());
        assertEquals("Not found ticket in system. Please check data",exception.getMessage());
    }

}