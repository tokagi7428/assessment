package com.kbtg.bootcamp.posttest.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.TicketRequestDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.service.TicketService;
import com.kbtg.bootcamp.posttest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    UserService userService;

    @Mock
    TicketService ticketService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(userService, ticketService);

        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .alwaysDo((print())).build();
    }

    @Test
    @DisplayName("when perform on GETMapping: /admin/ should return Hello admin!")
    void shouldReturnHello() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(jsonPath("$", is("Hello admin!")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on PostMapping: /admin/lotteries should be return return username!")
    void createLotteries() throws Exception {
        // Create a lottery
        TicketRequestDto ticketDto = new TicketRequestDto();
        ticketDto.setPrice(100);
        ticketDto.setAmount(10);
        ticketDto.setTicket("000001");

        Map<String,String> mapTicket = new HashMap<>();
        mapTicket.put("ticket", ticketDto.getTicket());
        when(ticketService.createLotteries(ticketDto)).thenReturn(mapTicket);

        // Perform the POST request
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ticketDto)))
                .andExpect(jsonPath("$.ticket", is("000001")))
                .andExpect(content().json(asJsonString(mapTicket)))
                .andExpect(status().isCreated());
    }



    @Test
    @DisplayName("when perform on GetMapping: /admin/lotteries should be return List of ticketDto!")
    void getAllLotteries() throws Exception {
        // get ALL the lotteries
        List<TicketDto> list = new ArrayList<TicketDto>();
        TicketDto ticketDto1 = new TicketDto();
        ticketDto1.setTicket("000001");
        ticketDto1.setAmount(10);
        ticketDto1.setPrice(80);
        ticketDto1.setId(1);
        ticketDto1.setStatus("ACTIVE");
        ticketDto1.setActive(true);

        TicketDto ticketDto2 = new TicketDto();
        ticketDto2.setTicket("000002");
        ticketDto2.setAmount(20);
        ticketDto2.setPrice(80);
        ticketDto2.setId(2);
        ticketDto2.setStatus("ACTIVE");
        ticketDto2.setActive(true);
        list.add(ticketDto1);
        list.add(ticketDto2);

        when(ticketService.getAllLotteries()).thenReturn(list);

        // Perform the Get request
        mockMvc.perform(get("/admin/lotteries"))
                .andExpect(jsonPath("$[0].ticket").value("000001")) // Use matchers for JSON path
                .andExpect(jsonPath("$[0].price").value(80))
                .andExpect(jsonPath("$[0].amount").value(10))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on PutMapping: /admin/userEdit/{id} should return username!")
    public void editUser() throws Exception {
        // set userDto
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUserId("abcde12356");
        userDto.setUsername("user");
        userDto.setPassword("1234");
        userDto.setRoles(List.of("ACCOUNTING","USER"));
        userDto.setPermissions(List.of("READ", "WRITE","EDIT"));

        Map<String,String> mapUsername = new HashMap<>();
        mapUsername.put("username", userDto.getUsername());
        when(userService.editUser(eq(userDto), eq("abc123"))).thenReturn(mapUsername);

        // Perform the edit request
        mockMvc.perform(patch("/admin/userEdit/{id}","abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(mapUsername)))
                .andExpect(jsonPath("$.username",is("user")));
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