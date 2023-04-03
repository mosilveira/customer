package com.example.customer.exceptions.handler;

import com.example.customer.exceptions.CustomerNotFoundException;
import com.example.customer.exceptions.InvalidEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerExceptionHandlerTest {

    @InjectMocks
    private CustomerExceptionHandler customerExceptionHandler;

    @Test
    void handleCustomerNotFoundException() {
        CustomerNotFoundException exception = mock(CustomerNotFoundException.class);

        ResponseEntity<Object> responseEntity = customerExceptionHandler.handleCustomerNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void handleInvalidEmailException() {
        InvalidEmailException exception = mock(InvalidEmailException.class);

        ResponseEntity<Object> responseEntity = customerExceptionHandler.handleInvalidEmailException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleMethodArgumentNotValid() {
        Method method = CustomerExceptionHandler.class.getMethods()[0];
        MethodParameter methodParameter = new MethodParameter(method, 0);
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);
        HttpHeaders headers = mock(HttpHeaders.class);
        WebRequest request = mock(WebRequest.class);

        when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

        ResponseEntity<Object> responseEntity = customerExceptionHandler.handleMethodArgumentNotValid(exception, headers, HttpStatus.BAD_REQUEST, request);

        assert responseEntity != null;
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void handleHttpMessageNotReadable() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> responseEntity = customerExceptionHandler.handleHttpMessageNotReadable(exception, headers, HttpStatus.BAD_REQUEST, request);

        assert responseEntity != null;
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}