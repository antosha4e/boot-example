package com.bootexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created: antosha4e
 * Date: 13.05.16
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }
}