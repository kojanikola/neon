package com.neon.releasetracker.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends java.lang.Exception {

    HttpStatus status;
    String message;

    public CustomException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

}
