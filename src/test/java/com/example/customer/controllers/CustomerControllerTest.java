package com.example.customer.controllers;

import com.example.customer.dto.request.CustomerRequestDTO;
import com.example.customer.dto.response.CustomerResponseDTO;
import com.example.customer.exceptions.CustomerNotFoundException;
import com.example.customer.exceptions.InvalidEmailException;
import com.example.customer.services.CustomerService;
import com.example.customer.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    public static final String BASE_URL = "/v1/customers";
    public static final String ID_URL = "/v1/customers/1";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void createCustomer_success() throws Exception {
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);

        when(customerService.createCustomer(any())).thenReturn(CustomerResponseDTO.builder().build());

        String input = Utils.mapToString(request);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createCustomer_exception_methodArgumentNotValid() throws Exception {
        CustomerRequestDTO request = new CustomerRequestDTO();

        when(customerService.createCustomer(any())).thenReturn(CustomerResponseDTO.builder().build());

        String input = Utils.mapToString(request);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void createCustomer_exception_invalidEmailException() throws Exception {
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);

        when(customerService.createCustomer(any())).thenThrow(new InvalidEmailException(request.getEmail()));

        String input = Utils.mapToString(request);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void createCustomer_exception_httpMessageNotReadable() throws Exception {
        String input = "HttpMessageNotReadable";

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void findCustomerById_success() throws Exception {
        when(customerService.findCustomerById(any())).thenReturn(CustomerResponseDTO.builder().build());

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void findCustomerById_exception_customerNotFoundException() throws Exception {
        when(customerService.findCustomerById(any())).thenThrow(new CustomerNotFoundException(1));

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void findAllCustomers_success() throws Exception {
        when(customerService.findAllCustomers()).thenReturn(Collections.singletonList(CustomerResponseDTO.builder().build()));

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void updateCustomer_success() throws Exception {
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);

        when(customerService.updateCustomer(any(), any())).thenReturn(CustomerResponseDTO.builder().build());

        String input = Utils.mapToString(request);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteCustomer_success() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.delete(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}