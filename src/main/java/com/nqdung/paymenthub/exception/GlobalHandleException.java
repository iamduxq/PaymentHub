package com.nqdung.paymenthub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> HandlingException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
