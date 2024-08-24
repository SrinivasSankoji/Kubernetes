package com.service.jokes.advice;

import com.service.jokes.dto.ApiResponse;
import com.service.jokes.exception.APIException;
import com.service.jokes.exception.ResourceNotFoundException;
import com.service.jokes.exception.ValidationException;
import com.service.jokes.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> handleException(HttpServletRequest request, Exception ex) {
        List<String> errors = Arrays.asList(ex.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "An error occurred", 500, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler (ResourceNotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
        ApiResponse<Void> response = ResponseUtil.error(ex.getMessage(), "Resource not found", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> handleValidationException(HttpServletRequest request, ValidationException ex) {
        List<String> errors = Arrays.asList(ex.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Validation failed", 400, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (APIException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> handleJokeSaveException(HttpServletRequest request, APIException ex) {
        ApiResponse<Void> response = ResponseUtil.error(ex.getMessage(), "API exception", 500, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
