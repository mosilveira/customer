package com.example.customer.exceptions.handler;

import com.example.customer.dto.exception.ApiExceptionResponse;
import com.example.customer.exceptions.CustomerNotFoundException;
import com.example.customer.exceptions.InvalidEmailException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class CustomerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return createResponse(HttpStatus.NOT_FOUND, exception.getMessage(), Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException exception) {
        return createResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), Collections.singletonList(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();

        List<String> errors = new ArrayList<>();

        fieldErrors.forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
        globalErrors.stream().map(error -> error.getObjectName() + ": " + error.getDefaultMessage()).forEach(errors::add);

        return createResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return createResponse(HttpStatus.BAD_REQUEST, "Malformed JSON body and/or field error", Collections.singletonList(ex.getLocalizedMessage()));
    }

    private ResponseEntity<Object> createResponse(HttpStatus http, String message, List<String> details) {
        ApiExceptionResponse response = ApiExceptionResponse.builder()
                .code(http.value())
                .status(http.getReasonPhrase())
                .message(message)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(http).body(response);
    }

}
