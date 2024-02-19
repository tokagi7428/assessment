package com.kbtg.bootcamp.posttest.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ResponseDto {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Object data;

    public ResponseDto(LocalDateTime timestamp, int status, Object data, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
