package com.kbtg.bootcamp.posttest.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.dto.TicketDto;
import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.service.TicketService;
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
    TicketService ticketService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(userService, ticketService);

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
        // Create a lottery
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPrice(100);
        ticketDto.setAmount(10);
        ticketDto.setTicketId("000001");

        ResponseDto responseDto = new ResponseDto(
                any(),
                HttpStatus.CREATED.value(),
                "000001",
                "create lottery successfully"
        );
        when(ticketService.createLotteries(ticketDto)).thenReturn(responseDto);

        // Perform the POST request
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ticketDto)))
//                .andExpect(jsonPath("$.timestamp", is(LocalDateTime.now())))
                .andExpect(jsonPath("$.status", is(201)))
                .andExpect(jsonPath("$.data", is("000001")))
                .andExpect(jsonPath("$.message", is("create lottery successfully")))
                .andExpect(status().isOk());
    }



    @Test
    @DisplayName("when perform on Post: /lotteries should return list!")
    void getAllLotteries() throws Exception {
        // get ALL the lotteries
        List<TicketDto> list = new ArrayList<TicketDto>();
        TicketDto ticketDto1 = new TicketDto();
        ticketDto1.setTicketId("000001");
        ticketDto1.setAmount(10);
        ticketDto1.setPrice(80);
        ticketDto1.setId(1);
        ticketDto1.setStatus("ACTIVE");
        ticketDto1.setActive(true);

        TicketDto ticketDto2 = new TicketDto();
        ticketDto2.setTicketId("000002");
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
                .andExpect(jsonPath("$[0].ticketId").value("000001")) // Use matchers for JSON path
                .andExpect(jsonPath("$[0].price").value(80))
                .andExpect(jsonPath("$[0].amount").value(10))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when performing PATCH on /lottery/1 should return ResponseDto")
    public void editLottery() throws Exception {
        // edit a lottery
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1);
        ticketDto.setPrice(100);
        ticketDto.setAmount(10);
        ticketDto.setActive(true);
        ticketDto.setTicketId("000001");
        ticketDto.setStatus("Active");

        // Mock the service method to return a response
        ResponseDto responseDto = new ResponseDto(null, HttpStatus.OK.value(), ticketDto, "Update lottery successfully");
        when(ticketService.editLottery(eq(ticketDto), eq(1))).thenReturn(responseDto);

        // Perform the PATCH request
        mockMvc.perform(patch("/admin/lottery/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ticketDto)))
                // Verify the response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.price").value(100))
                .andExpect(jsonPath("$.data.amount").value(10))
                .andExpect(jsonPath("$.data.active").value(true))
                .andExpect(jsonPath("$.data.status").value("Active"))
                .andExpect(jsonPath("$.message").value("Update lottery successfully"));
    }



    @Test
    @DisplayName("when perform on DeleteMapping: /lottery/{id} should return ResponseDto!")
    public void deleteLottery() throws Exception {

        // delete a lottery
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1);
        ticketDto.setPrice(100);
        ticketDto.setAmount(10);
        ticketDto.setStatus("ACTIVE");
        ticketDto.setTicketId("000001");
        ticketDto.setActive(true);

        // Mock the service method to return a response
        ResponseDto responseDto = new ResponseDto(null, HttpStatus.OK.value(), null, "deleted this ticket : " + ticketDto.getTicketId() + " successfully");
        when(ticketService.deleteLottery(eq(ticketDto), eq(1))).thenReturn(responseDto);

        // Perform the DELETE request
        mockMvc.perform(delete("/admin/lottery/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ticketDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("deleted this ticket : " + ticketDto.getTicketId() + " successfully"));
    }

    @Test
    @DisplayName("when perform on PatchMapping: /userEdit/{userId} should return ResponseDto!")
    public void editUser() throws Exception {
        // set userDto
        UserDto userDto = new UserDto();
        userDto.setUserId("abc123");
        userDto.setUsername("user");
        userDto.setPassword("1234");
        userDto.setRoles(List.of("ACCOUNTING","USER"));
        userDto.setPermissions(List.of("READ", "WRITE","EDIT"));

        ResponseDto responseDto = new ResponseDto(null, HttpStatus.OK.value(), null, "update username : " + userDto.getUsername() + " successfully!");
        when(userService.editUser(eq(userDto), eq("abc123"))).thenReturn(responseDto);

        // Perform the edit request
        mockMvc.perform(patch("/admin/userEdit/{id}","abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message").value("update username : " + userDto.getUsername() + " successfully!"));
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