package com.kbtg.bootcamp.posttest.serviceImpl;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.dto.UserRequestDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;


    @Test
    @DisplayName("when loadUserByUsername should be username UserDetails!")
    void loadUserByUsername() {
        String username = "user";
        UserModel userModel = new UserModel();
        userModel.setUsername("user");
        userModel.setPassword("password");
        userModel.setPermissions(List.of("USER"));
        userModel.setRoles(List.of("USER"));
        userModel.setUserId("abcde12345");

        when(userService.loadUserByUsername(username)).thenReturn(userModel);
        assertEquals(userModel,userService.loadUserByUsername(username));

        when(userService.loadUserByUsername(username)).thenReturn(null);
        assertNull(userService.loadUserByUsername(username));
    }

    @Test
    @DisplayName("when findByUsername should be username UserModel!")
    void findByUsername() {
        String username = "user";
        UserModel userModel = new UserModel();
        userModel.setUsername("user");
        userModel.setPassword("password");
        userModel.setPermissions(List.of("USER"));
        userModel.setRoles(List.of("USER"));
        userModel.setUserId("abcde12345");
        userRepository.save(userModel);
        when(userService.findByUsername(username)).thenReturn(Optional.of(userModel));
        assertEquals(Optional.of(userModel),userService.findByUsername(username));

        when(userService.findByUsername(username)).thenReturn(null);
        assertNull(userService.findByUsername(username));
    }

    @Test
    @DisplayName("when createUser should be return username")
    void createUser() {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setUsername("newUser");
        userDto.setPassword("password");

        UserModel userModel = new UserModel();
        userModel.setUsername(userDto.getUsername());

        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());

        Map<String, String> result = userService.createUser(userDto);
        assertEquals(userDto.getUsername(), result.get("username"));
    }

    @Test
    @DisplayName("Create User throw BadRequest")
    void createUser_UserBadRequest() {
        UserRequestDto userDto = new UserRequestDto();
        userDto.setUsername("newUser");
        userDto.setPassword("password");

        UserModel userModel = new UserModel();
        userModel.setUserId("abcde12345");
        userModel.setUsername("existingUser");

        lenient().when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userModel));

        var exception = assertThrows(BadRequestException.class, () -> userService.createUser(userDto));
        assertEquals("User already exists",exception.getMessage());
    }

    @Test
    @DisplayName("Edit User Success")
    void editUser_Success() {
        UserDto userDto = new UserDto();
        userDto.setUsername("newUsername");
        userDto.setRoles(List.of("ADMIN"));
        userDto.setPermissions(List.of("ADMIN"));

        UserModel userModel = new UserModel();
        userModel.setUserId("abcde12345");
        userModel.setUsername("existingUser");

        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(userModel));

        Map<String, String> result = userService.editUser(userDto, "abcde12345");

        assertEquals("newUsername", result.get("username"));
    }

    @Test
    @DisplayName("Edit User NotFoundException()")
    void editUser_UserNotFound() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user");
        userDto.setPassword("password");
        userDto.setPermissions(List.of("USER"));
        userDto.setRoles(List.of("USER"));
        userDto.setUserId("abcde12345");

        lenient().when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> userService.editUser(userDto, "abcde12345"));
        assertEquals("User not found",exception.getMessage());
    }

    private String asJsonString(Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}