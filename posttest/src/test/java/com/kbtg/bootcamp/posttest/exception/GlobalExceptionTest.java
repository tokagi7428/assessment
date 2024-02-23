package com.kbtg.bootcamp.posttest.exception;

import static org.mockito.Mockito.*;
import com.kbtg.bootcamp.posttest.dto.ResponseErrorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionTest {

    @InjectMocks
    private GlobalException globalException;

    @Mock
    private BadRequestException badRequestException;

    @Mock
    private NotFoundException notFoundException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Test
    @DisplayName("handleBadRequestException!")
    public void handleBadRequestException() {
        String errorMessage = "Invalid input";
        when(badRequestException.getMessage()).thenReturn(errorMessage);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        WebRequest request = new ServletWebRequest(mockRequest);

        ResponseErrorDto responseErrorDto = globalException.handleBadRequestException(badRequestException, request);
        System.out.println(responseErrorDto.toString());

        assertEquals(400, responseErrorDto.getStatus());
        assertEquals(errorMessage, responseErrorDto.getMessage());
        assertEquals("Bad Request", responseErrorDto.getError());
        assertNotNull(responseErrorDto.getTimestamp());
        assertNotNull(responseErrorDto.getPath());
    }

    @Test
    @DisplayName("handleNotFoundRequestException!")
    void handleNotFoundRequestException() {
        String errorMessage = "Not Found";
        when(notFoundException.getMessage()).thenReturn(errorMessage);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        WebRequest request = new ServletWebRequest(mockRequest);

        ResponseErrorDto responseErrorDto = globalException.handleNotFoundRequestException(notFoundException, request);
        System.out.println(responseErrorDto.toString());

        assertEquals(404, responseErrorDto.getStatus());
        assertEquals(errorMessage, responseErrorDto.getMessage());
        assertEquals("Not Found", responseErrorDto.getError());
        assertNotNull(responseErrorDto.getTimestamp());
        assertNotNull(responseErrorDto.getPath());
    }

    @Test
    @DisplayName("handleMethodArgumentNotValidException!")
    void handleMethodArgumentNotValidException() {
        List<FieldError> errorMessage = List.of();
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(errorMessage);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        WebRequest request = new ServletWebRequest(mockRequest);

        ResponseErrorDto responseErrorDto = globalException.handleMethodArgumentNotValidException(methodArgumentNotValidException, request);
        System.out.println(responseErrorDto.toString());

        assertNotNull(responseErrorDto);
    }
}