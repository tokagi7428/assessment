package com.kbtg.bootcamp.posttest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.service.TicketService;
import com.kbtg.bootcamp.posttest.service.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Mock
    UserTicketService userTicketService;

    @Mock
    private TicketService ticketService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TicketController ticketController = new TicketController(userTicketService, ticketService);

        mockMvc = MockMvcBuilders.standaloneSetup(ticketController)
                .alwaysDo(print()).build();
    }

    @Test
    @DisplayName("when perform on get: /public/lotteries/ticket should return public ticket!")
    void getTickets() throws Exception {
        mockMvc.perform(get("/public/lotteries/ticket"))
                .andExpect(jsonPath("$", is("public ticket!")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on GetMapping: /public/lotteries/{userId} should return public tickets")
    void getLotteries() throws Exception {
        String userId = "7c941c104a";
        Map<String, List<String>> userTicketMap = new LinkedHashMap<>();
        List<String> userTicketSet = new ArrayList<>();
        userTicketSet.add("000001");
        userTicketSet.add("000005");
        userTicketMap.put("tickets", userTicketSet);
        when(userTicketService.getLotteries("7c941c104a")).thenReturn(userTicketMap);

        mockMvc.perform(get("/public/lotteries/{userId}",userId))
                .andExpect(jsonPath("$.tickets[0]", is("000001")))
                .andExpect(jsonPath("$.tickets[1]", is("000005")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on GetMapping: /public/lotteries should return list of tickets")
    void getAllLotteries_Successful() throws Exception {
        Map<String, List<String>> mockLotteries = Map.of("tickets", List.of("123456", "123455"));
        lenient().when(ticketService.getAllLotteriesUser()).thenReturn(mockLotteries);

        mockMvc.perform(get("/public/lotteries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tickets[0]", is("123456")))
                .andExpect(jsonPath("$.tickets[1]", is("123455")));
    }

    // convert object to json
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}