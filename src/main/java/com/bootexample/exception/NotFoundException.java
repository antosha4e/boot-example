package com.bootexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created: antosha4e
 * Date: 13.05.16
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)  // 404
public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}