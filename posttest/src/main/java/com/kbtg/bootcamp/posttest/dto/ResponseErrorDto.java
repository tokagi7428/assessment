package com.kbtg.bootcamp.posttest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseErrorDto {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ResponseErrorDto(){}

    public ResponseErrorDto(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
