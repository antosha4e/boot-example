package com.bootexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created: antosha4e
 * Date: 13.05.16
 */
@ResponseStatus(value= HttpStatus.CONFLICT)  // 409
public class ConflictException extends RuntimeException {
    public ConflictException(String msg) {
        super(msg);
    }
}