package com.test.backend.embedapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/*
*  예외 처리 응답용 DTO
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDateTime localDateTime;
    private String message;
    private String details;
}
