package com.example.trading.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler
{

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(RecordNotFoundException ex)
    {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage(), LocalDateTime
            .now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    //Global Exception
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleUnAuthenticException(Exception ex)
    {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage(), LocalDateTime
            .now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorResponse> handleUnAuthenticRuntime(RuntimeException ex)
    {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_GATEWAY.value(),ex.getMessage(), LocalDateTime
            .now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_GATEWAY);
    }
    
    @ExceptionHandler(TradeException.class)
    public final ResponseEntity<ErrorResponse> tradeException(TradeException tradeException){
    	ErrorResponse error= new ErrorResponse(HttpStatus.BAD_REQUEST.value(), tradeException.getMessage(), LocalDateTime.now());
    	return new ResponseEntity<ErrorResponse>(error,HttpStatus.BAD_REQUEST);
    }
    
   

}