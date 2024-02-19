package com.kbtg.bootcamp.posttest.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.dto.LotteryDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.service.LotteryService;
import com.kbtg.bootcamp.posttest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    UserService userService;

    @Mock
    LotteryService lotteryService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(userService,lotteryService);

        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo((print())).build();
    }

    @Test
    @DisplayName("when perform on GET: /admin should return Hello admin!")
    void shouldReturnHello() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(jsonPath("$", is("Hello admin!")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on Post: /lotteries should return responseDto!")
    void createLotteries() throws Exception {
        // Create a sample user DTO
        LotteryDto lotteryDto = new LotteryDto();
        lotteryDto.setPrice(100);
        lotteryDto.setAmount(10);
        lotteryDto.setTicket("000001");

        // Mock the userService.createUser() method to return a predefined ResponseDto
        ResponseDto responseDto = new ResponseDto(
                any(), // Assuming this timestamp is generated within the service
                HttpStatus.CREATED.value(),
                "000001",
                "create lottery successfully"
        );
        when(lotteryService.createLotteries(lotteryDto)).thenReturn(responseDto);

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(lotteryDto)))
                // Verify the response
//                .andExpect(jsonPath("$.timestamp", is(LocalDateTime.now())))
                .andExpect(jsonPath("$.status", is(201)))
                .andExpect(jsonPath("$.data", is("000001")))
                .andExpect(jsonPath("$.message", is("create lottery successfully")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on Post: /lotteries should return list!")
    void getAllLotteries() throws Exception {
        // Create a sample user DTO
        List<LotteryDto> list = new ArrayList<LotteryDto>();
        LotteryDto lotteryDto1 = new LotteryDto();
        lotteryDto1.setTicket("000001");
        lotteryDto1.setAmount(10);
        lotteryDto1.setPrice(80);
        lotteryDto1.setId(1);
        lotteryDto1.setStatus("ACTIVE");
        lotteryDto1.setActive(true);

        LotteryDto lotteryDto2 = new LotteryDto();
        lotteryDto2.setTicket("000002");
        lotteryDto2.setAmount(20);
        lotteryDto2.setPrice(80);
        lotteryDto2.setId(2);
        lotteryDto2.setStatus("ACTIVE");
        lotteryDto2.setActive(true);
        list.add(lotteryDto1);
        list.add(lotteryDto2);

        // Use Matchers for stubbing
        when(lotteryService.getAllLotteries()).thenReturn(list);

        mockMvc.perform(get("/admin/lotteries"))
                .andExpect(jsonPath("$[0].ticket").value("000001")) // Use matchers for JSON path
                .andExpect(jsonPath("$[0].price").value(80))
                .andExpect(jsonPath("$[0].amount").value(10))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(status().isOk());
    }


    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}