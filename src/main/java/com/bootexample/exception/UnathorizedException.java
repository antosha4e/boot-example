package com.bootexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created: antosha4e
 * Date: 13.05.16
 */
@ResponseStatus(value= HttpStatus.UNAUTHORIZED)  // 401
public class UnathorizedException extends RuntimeException {
    public UnathorizedException(String msg) {
        super(msg);
    }
}