package com.neon.releasetracker.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(java.lang.Exception.class)
    @ResponseBody
    ResponseEntity<String> handleGenericRequest(HttpServletRequest req, java.lang.Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    ResponseEntity<String> handleBadRequest(HttpServletRequest req, CustomException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity(ex.getMessage(),ex.getStatus());
    }

}

