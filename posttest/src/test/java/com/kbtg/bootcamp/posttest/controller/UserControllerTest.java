package com.kbtg.bootcamp.posttest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.dto.TicketResponse;
import com.kbtg.bootcamp.posttest.dto.UserRequestDto;
import com.kbtg.bootcamp.posttest.service.UserService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    UserTicketService userTicketService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userService, userTicketService);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .alwaysDo(print()).build();
    }

    @Test
    @DisplayName("when perform on get: /users should return Hello user!")
    void helloUser() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$", is("Hello user!")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on post: /users/createUser should return username")
    void createUser() throws Exception {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setUsername("admin");
        userDto.setPassword("password");
        Map<String, String> mapDataResponse = new HashMap<String, String>();
        mapDataResponse.put("username", userDto.getUsername());

        when(userService.createUser(userDto)).thenReturn(mapDataResponse);

        mockMvc.perform(post("/users/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(jsonPath("$.username", is("admin")))
                .andExpect(content().json(asJsonString(mapDataResponse)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("when perform on PostMapping: /users/12345abcde/lotteries/1 should return id!")
    public void buyLottery_Success() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "1");

        when(userTicketService.buyLottery("12345abcde", 1)).thenReturn(requestBody);

        mockMvc.perform(post("/users/12345abcde/lotteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on PostMapping: /users/12345abcde/lotteries/1 should return ticket!")
    public void sellLottery_Success() throws Exception {
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("userTicketId", 1);

        Map<String, String> response = new HashMap<>();
        response.put("ticket", "000001");

        when(userTicketService.sellLottery(any(String.class), any(Integer.class))).thenReturn(response);

        mockMvc.perform(delete("/users/12345abcde/lotteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(jsonPath("$.ticket", is("000001")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on GetMapping: /users/{userId}/lotteries should return data!")
    void getMyLotteries_Success() throws Exception {
        TicketResponse mockResponse = new TicketResponse(List.of("ticket1", "ticket2"), 2, 300);
        when(userTicketService.getMyLotteries("userId")).thenReturn(mockResponse);

        mockMvc.perform(get("/users/{userId}/lotteries", "userId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count", is(2)))
                .andExpect(jsonPath("$.cost", is(300)));
    }

    private String asJsonString(Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}