package com.nqdung.paymenthub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> HandlingException(RuntimeException exception) {
        String errMessage = exception.getMessage();

        if (exception.getCause() != null && (errMessage == null || !errMessage.contains("ORA-"))) {
            errMessage = exception.getCause().getMessage();
        }

        if (errMessage != null && errMessage.contains("ORA-")) {
            errMessage = errMessage.substring(errMessage.indexOf(":") + 1).trim();
            if (errMessage.contains("ORA-")) {
                errMessage = errMessage.split("ORA-")[0].trim();
            }
            return ResponseEntity.badRequest().body(errMessage);
        }
        exception.printStackTrace();
        return ResponseEntity.status(500).body("Lỗi hệ thống: " + exception.getMessage());
    }
}
