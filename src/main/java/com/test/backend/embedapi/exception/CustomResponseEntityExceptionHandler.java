package com.test.backend.embedapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.LocalDateTime.*;

@RestController
@ControllerAdvice
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /*
    *  요청 파라미터에서 발생한하는 예외에 대한 처리 4XX
    * */
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleValidatedException(Exception ex, WebRequest request){
        log.info("{} request, but {}",request.getDescription(true),ex.getMessage());
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(now(),ex.getMessage(),request.getDescription(true));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /*
    *   서버단에서 발생하는 기타 예외에 대한 처리 5XX
    * */
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request){
        log.info("{} request, but {}",request.getDescription(true),ex.getMessage());
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(now(),ex.getMessage(),request.getDescription(true));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
