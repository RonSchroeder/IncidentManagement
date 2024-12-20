package com.example.incidentManagement.exception;

import com.example.incidentManagement.common.ErrorCode;
import com.example.incidentManagement.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // when a CustomException is thrown, the errorCode is set to FAILURE
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Result<Void>> handleCustomException(CustomException ex) {
        return ResponseEntity.badRequest().body(Result.error(ErrorCode.FAILURE.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError().body(Result.error(ErrorCode.FAILURE.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getField()).append(":").append(error.getDefaultMessage()).append("; ");
        });
        return ResponseEntity.badRequest().body(Result.error(ErrorCode.FAILURE.getCode(), errorMessage.toString()));
    }
}
