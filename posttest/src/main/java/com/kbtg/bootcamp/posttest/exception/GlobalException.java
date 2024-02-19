package com.kbtg.bootcamp.posttest.exception;

import com.kbtg.bootcamp.posttest.dto.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseErrorDto handleBadRequestException(BadRequestException notValidException, WebRequest request) {
        return new ResponseErrorDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                notValidException.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseErrorDto handleBadRequestException(MethodArgumentNotValidException notValidException, WebRequest request) {
        List<String> error = notValidException.getFieldErrors()
                .stream()
                .map(f -> f.getField()  + " " + f.getDefaultMessage())
                .toList();

        return new ResponseErrorDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                String.join(", ", error),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseErrorDto handleNotFoundRequestException(NotFoundException notValidException, WebRequest request) {
        return new ResponseErrorDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                notValidException.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = {InternalServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseErrorDto handleInternalServerException(InternalServerException internalServerException, WebRequest request) {
        return new ResponseErrorDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                internalServerException.getMessage(),
                request.getDescription(false)
        );
    }
}
