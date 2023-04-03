package com.example.customer.services;

import com.example.customer.dto.request.CustomerRequestDTO;
import com.example.customer.dto.response.CustomerResponseDTO;
import com.example.customer.entities.CustomerEntity;
import com.example.customer.exceptions.CustomerNotFoundException;
import com.example.customer.exceptions.InvalidEmailException;
import com.example.customer.repositories.CustomerRepository;
import com.example.customer.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    public static final Integer ID = 1;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void createCustomer_success() throws IOException {
        CustomerEntity customer = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);
        CustomerResponseDTO expectedResponse = Utils.readFromFile("/json/customerResponse.json", CustomerResponseDTO.class);

        when(customerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);

        CustomerResponseDTO response = customerService.createCustomer(request);

        assertEquals(expectedResponse, response);
        verify(customerRepository).save(any());
    }

    @Test
    void createCustomer_exception_invalidEmailException() throws IOException {
        CustomerEntity customer = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);

        when(customerRepository.findByEmail(any())).thenReturn(Optional.of(customer));

        assertThrows(InvalidEmailException.class, () -> customerService.createCustomer(request));
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void findCustomerById_success() throws IOException {
        CustomerEntity customer = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);
        CustomerResponseDTO expectedResponse = Utils.readFromFile("/json/customerResponse.json", CustomerResponseDTO.class);

        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));

        CustomerResponseDTO response = customerService.findCustomerById(ID);

        assertEquals(expectedResponse, response);
        assertNotNull(response);
        verify(customerRepository).findById(any());
    }

    @Test
    void findCustomerById_exception_customerNotFoundException() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById(ID));
    }

    @Test
    void findAllCustomers_success() throws IOException {
        CustomerEntity customer = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);

        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        List<CustomerResponseDTO> response = customerService.findAllCustomers();

        assertEquals(1, response.size());
    }

    @Test
    void updateCustomer_success() throws IOException {
        CustomerEntity customer = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);
        CustomerResponseDTO expectedResponse = Utils.readFromFile("/json/customerResponse.json", CustomerResponseDTO.class);

        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        when(customerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);

        CustomerResponseDTO response = customerService.updateCustomer(ID, request);

        assertEquals(expectedResponse, response);
        verify(customerRepository).findById(any());
        verify(customerRepository).findByEmail(any());
        verify(customerRepository).save(any());
    }

    @Test
    void updateCustomer_exception_customerNotFoundException() throws IOException {
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);

        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(ID, request));
        verify(customerRepository).findById(any());
        verify(customerRepository, times(0)).findByEmail(any());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void updateCustomer_exception_invalidEmailException() throws IOException {
        CustomerRequestDTO request = Utils.readFromFile("/json/customerRequest.json", CustomerRequestDTO.class);
        CustomerEntity customer = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);
        CustomerEntity customerFoundByEmail = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);
        customerFoundByEmail.setId(1001);

        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        when(customerRepository.findByEmail(any())).thenReturn(Optional.of(customerFoundByEmail));

        assertThrows(InvalidEmailException.class, () -> customerService.updateCustomer(ID, request));
        verify(customerRepository).findById(any());
        verify(customerRepository).findByEmail(any());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void deleteCustomer() throws IOException {
        CustomerEntity customer = Utils.readFromFile("/json/customerEntity.json", CustomerEntity.class);

        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));

        customerService.deleteCustomer(ID);

        verify(customerRepository).deleteById(any());
    }

}