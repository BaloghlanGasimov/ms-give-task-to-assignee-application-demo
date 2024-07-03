package com.example.msgivetaskstoassignee.controller;

import com.example.msgivetaskstoassignee.exceptions.NotFoundException;
import com.example.msgivetaskstoassignee.model.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handler(Exception e){
        e.printStackTrace();
        return new ExceptionDto("UNEXPECTED ERROR");
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handler(NotFoundException e){
        log.error(e.getLogMessage());
        return new ExceptionDto(e.getErrorMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionDto> handler(MethodArgumentNotValidException e){
        List<ExceptionDto> exceptionDtos=new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(
                (error)->
                exceptionDtos.add(new ExceptionDto(error.getField()+" : "+error.getDefaultMessage()))
        );

        return exceptionDtos;
    }
}
