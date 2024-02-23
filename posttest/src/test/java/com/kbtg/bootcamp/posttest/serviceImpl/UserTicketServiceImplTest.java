package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.TicketResponse;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.TicketModel;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.model.UserTicketModel;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTicketServiceImplTest {

    @InjectMocks
    UserTicketServiceImpl userTicketService;

    @Mock
    UserTicketRepository userTicketRepository;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("getLotteries_NotFound should be NotFoundException")
    public void getLotteries_NotFound() {
        when(userTicketRepository.findByUserIdAndTransactionalType(anyString(), anyString()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class,() -> userTicketService.getLotteries("abcde12345"));
        assertEquals("Ticket not found",exception.getMessage());
    }

    @Test
    @DisplayName("getLotteries_Success should be tickets")
    void getLotteries_Success() {
        String userId = "abcde12345";
        UserTicketModel ticketModel1 = new UserTicketModel();
        ticketModel1.setUserId(userId);
        ticketModel1.setTicketId(1);
        ticketModel1.setId(1);
        ticketModel1.setTransactionType("BUY");

        Optional<List<UserTicketModel>> ticketModels = Optional.of(Arrays.asList(ticketModel1));

        TicketModel ticketModel = new TicketModel();
        ticketModel.setId(1);
        ticketModel.setTicket("000001");
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticketModel));

        when(userTicketRepository.findByUserIdAndTransactionalType(userId, "BUY")).thenReturn(ticketModels);

        Map<String,List<String>> tickets = userTicketService.getLotteries(userId);
        assertNotNull(tickets);
        assertEquals(1, tickets.get("tickets").size());
        assertTrue(tickets.get("tickets").contains("000001"));
    }

    @Test
    @DisplayName("buyLottery_Success should be id")
    public void buyLottery_Success() {
        UserModel userModel = new UserModel();
        userModel.setUserId("123456abcd");
        TicketModel ticketModel = new TicketModel();
        ticketModel.setId(1);
        ticketModel.setAmount(2);
        when(userRepository.findByUserId("123456abcd")).thenReturn(Optional.of(userModel));
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticketModel));

        Map<String, String> result = userTicketService.buyLottery( "123456abcd", 1);

        assertEquals(1, ticketModel.getAmount());
        verify(userTicketRepository, times(1)).save(any(UserTicketModel.class));

        assertEquals("1", result.get("id"));
    }

    @Test
    @DisplayName("when userTicketService.buyLottery() should be return error BadRequestException()")
    void buyLottery_NotFound() {

        lenient().when(userRepository.findByUserId(anyString()))
                .thenReturn(Optional.empty());

        lenient().when(ticketRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class,() -> userTicketService.buyLottery("12345abcde",2));

        assertEquals("Not found user or ticket",exception.getMessage());
    }

    @Test
    @DisplayName("when userTicketService.buyLottery() should be return error BadRequestException()")
    void buyLottery_BadRequest() {
        UserModel user = new UserModel();
        user.setId(1);
        user.setUserId("abcde12345");
        lenient().when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));

        TicketModel ticketModel = new TicketModel();
        ticketModel.setTicket("123456");
        ticketModel.setAmount(0);
        lenient().when(ticketRepository.findById(anyInt())).thenReturn(Optional.of(ticketModel));

        lenient().when(userTicketRepository.findByIdAndTransactionalTypeAndUserId(1, "BUY","abcde12345")).thenReturn(Optional.empty());

        UserTicketServiceImpl userTicketService = new UserTicketServiceImpl(userTicketRepository,ticketRepository,userRepository);

        var exception = assertThrows(BadRequestException.class,() -> userTicketService.buyLottery("abcde12345",1));
        assertEquals("The ticket is not enough",exception.getMessage());
    }

    @Test
    @DisplayName("when ticketService.deleteLottery() should be return Map<String,String>")
    void sellLottery() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setUsername("user");
        userModel.setRoles(List.of("USER"));
        userModel.setPermissions(List.of("USER"));
        userModel.setUserId("12345abcde");

        TicketModel ticketModel = new TicketModel();
        ticketModel.setId(1);
        ticketModel.setPrice(100);
        ticketModel.setTicket("123456");
        ticketModel.setAmount(100);
        ticketModel.setStatus("ACTIVE");

        UserTicketModel userTicketModel = new UserTicketModel();
        userTicketModel.setId(1);
        userTicketModel.setTransactionBuyDate(LocalDateTime.now());
        userTicketModel.setTransactionType("BUY");
        userTicketModel.setTicketId(1);
        userTicketModel.setUserId("12345abcde");

        List<UserTicketModel> userTicketModelList = new ArrayList<UserTicketModel>();
        userTicketModelList.add(userTicketModel);

        lenient().when(userRepository.findByUserId("12345abcde")).thenReturn(Optional.of(userModel));
        lenient().when(ticketRepository.findById(1)).thenReturn(Optional.of(ticketModel));
        lenient().when(userTicketRepository.findByTicketIdAndTransactionalTypeAndUserId(1, "BUY", "12345abcde")).thenReturn(Optional.of(userTicketModelList));

        Map<String, String> result = userTicketService.sellLottery("12345abcde", 1);

        assertNotNull(result);
        assertEquals("123456", result.get("ticket"));
    }

    @Test
    @DisplayName("when userTicketService.sellLottery() should be return error BadRequestException()")
    void sellLottery_BadRequest() {
        UserModel user = new UserModel();
        user.setUserId("abcde12345");
        lenient().when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));

        TicketModel ticketModel = new TicketModel();
        ticketModel.setTicket("123456");
        lenient().when(ticketRepository.findById(anyInt())).thenReturn(Optional.of(ticketModel));

        lenient().when(userTicketRepository.findByTicketIdAndTransactionalTypeAndUserId(1, "BUY","abcde12345")).thenReturn(Optional.empty());

        var exception = assertThrows(BadRequestException.class,() -> userTicketService.sellLottery("abcde12345",9));
        assertEquals("Not found this ticket for buy",exception.getMessage());
    }

    @Test
    @DisplayName("when userTicketService.sellLottery() should be return error BadRequestException()")
    void sellLottery_NotFound() {
        lenient().when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        lenient().when(ticketRepository.findByTicket(anyString())).thenReturn(Optional.empty());

        UserTicketServiceImpl userTicketService = new UserTicketServiceImpl(userTicketRepository,ticketRepository,userRepository);

        var exception = assertThrows(NotFoundException.class,() -> userTicketService.sellLottery("abcde12345",1));
        assertEquals("Not found user or ticket",exception.getMessage());
    }

    @Test
    @DisplayName("getMyLotteries_Successful should be return TicketResponse")
    void getMyLotteries_Successful() {
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setUsername("user");
        userModel.setRoles(List.of("USER"));
        userModel.setPermissions(List.of("USER"));
        userModel.setUserId("12345abcde");

        TicketModel ticketModel1 = new TicketModel();
        ticketModel1.setId(1);
        ticketModel1.setTicket("123456");
        ticketModel1.setPrice(100);

        TicketModel ticketModel2 = new TicketModel();
        ticketModel2.setId(2);
        ticketModel2.setTicket("000001");
        ticketModel2.setPrice(200);

        UserTicketModel userTicketModel1 = new UserTicketModel();
        userTicketModel1.setId(1);
        userTicketModel1.setTicketId(1);
        userTicketModel1.setUserId("12345abcde");

        UserTicketModel userTicketModel2 = new UserTicketModel();
        userTicketModel2.setId(2);
        userTicketModel2.setTicketId(2);
        userTicketModel2.setUserId("12345abcde");

        List<UserTicketModel> userTicketModelList = List.of(userTicketModel1, userTicketModel2);

        lenient().when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(userModel));
        lenient().when(userTicketRepository.findByUserIdAndTransactionalType("12345abcde","BUY")).thenReturn(Optional.of(userTicketModelList));
        lenient().when(ticketRepository.findById(1)).thenReturn(Optional.of(ticketModel1));
        lenient().when(ticketRepository.findById(2)).thenReturn(Optional.of(ticketModel2));

        TicketResponse result = userTicketService.getMyLotteries("12345abcde");

        assertEquals(2, result.count());
        assertEquals(300, result.cost());
        assertEquals(List.of("123456", "000001"), result.tickets());
    }

    @Test
    @DisplayName("getMyLotteries_UserNotFound should be return error NotFoundException()")
    void getMyLotteries_UserNotFound() {
        lenient().when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userTicketService.getMyLotteries("123456abcd"));
    }

    @Test
    @DisplayName("getMyLotteries_NoTicketsFoundUser should be return error BadRequestException()")
    void getMyLotteries_NoTicketsFoundUser() {
        UserModel userModel = new UserModel();
        userModel.setUserId("123456abcd");
        lenient().when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(userModel));
        lenient().when(userTicketRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> userTicketService.getMyLotteries("123456abcd"));
    }

}