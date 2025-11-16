package com.garage.backend.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", "Endpoint not found");
        response.put("message", "The requested endpoint does not exist. Please check the URL and try again.");
        response.put("path", ex.getRequestURL());
        response.put("method", ex.getHttpMethod());
        response.put("status", HttpStatus.NOT_FOUND.value());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<Map<String, Object>> handleUnexpectedRollbackException(UnexpectedRollbackException ex) {
        logger.error("UnexpectedRollbackException occurred", ex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        
        // Get the root cause for a more meaningful error message
        Throwable rootCause = getRootCause(ex);
        if (rootCause != null && rootCause != ex) {
            logger.error("Root cause: {}", rootCause.getMessage(), rootCause);
            response.put("message", "Transaction failed: " + rootCause.getMessage());
            response.put("rootCause", rootCause.getClass().getSimpleName());
        } else {
            response.put("message", "Transaction failed: " + ex.getMessage());
        }

        return ResponseEntity.badRequest().body(response);
    }
    
    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause == null || cause == throwable) {
            return throwable;
        }
        return getRootCause(cause);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        // Skip handling UnexpectedRollbackException here as it has its own handler
        if (ex instanceof UnexpectedRollbackException) {
            return handleUnexpectedRollbackException((UnexpectedRollbackException) ex);
        }
        
        logger.error("RuntimeException occurred", ex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        
        // Get root cause for better error messages
        Throwable rootCause = getRootCause(ex);
        if (rootCause != null && rootCause != ex) {
            response.put("message", rootCause.getMessage());
            response.put("rootCause", rootCause.getClass().getSimpleName());
        } else {
            response.put("message", ex.getMessage());
        }

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        logger.error("Unexpected exception occurred", ex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "An unexpected error occurred: " + ex.getMessage());
        response.put("exceptionType", ex.getClass().getSimpleName());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
} 