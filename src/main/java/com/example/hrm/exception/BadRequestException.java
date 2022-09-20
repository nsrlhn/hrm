package com.example.hrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {

    public BadRequestException(ExceptionCode code) {
        super(HttpStatus.BAD_REQUEST, code.toString());
    }
}
