package com.example.customer.services;

import com.example.customer.dto.request.CustomerRequestDTO;
import com.example.customer.dto.response.CustomerResponseDTO;
import com.example.customer.entities.CustomerEntity;
import com.example.customer.exceptions.CustomerNotFoundException;
import com.example.customer.exceptions.InvalidEmailException;
import com.example.customer.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
        log.info("Creating customer - request: {}", request);

        Optional<CustomerEntity> customerFound = customerRepository.findByEmail(request.getEmail());

        if (customerFound.isPresent()) {
            log.error("Error in creating user - e-mail already registered - request: {}", request);
            throw new InvalidEmailException(request.getEmail());
        }

        CustomerEntity customerToCreate = CustomerEntity.builder().customerRequest(request).build();
        CustomerEntity createdCustomer = customerRepository.save(customerToCreate);

        CustomerResponseDTO response = createCustomerResponse(createdCustomer);

        log.info("Customer created - response: {}", response);
        return response;
    }

    public CustomerResponseDTO findCustomerById(Integer id) {
        log.info("Looking for customer - id: {}", id);

        CustomerResponseDTO response = createCustomerResponse(getCustomerEntity(id));

        log.info("Customer found - response: {}", response);
        return response;
    }

    public List<CustomerResponseDTO> findAllCustomers() {
        log.info("Looking for all customers");

        List<CustomerResponseDTO> response = customerRepository.findAll().stream()
                .map(this::createCustomerResponse)
                .toList();

        String ids = response.stream().map(customer -> String.valueOf(customer.getId())).collect(Collectors.joining(","));
        log.info("Customer list found - ids: {}", ids);
        return response;
    }

    public CustomerResponseDTO updateCustomer(Integer id, CustomerRequestDTO request) {
        log.info("looking for user to update - id: {}, request: {}", id, request);

        getCustomerEntity(id);

        Optional<CustomerEntity> customerByEmail = customerRepository.findByEmail(request.getEmail());

        if (customerByEmail.isPresent() && !Objects.equals(customerByEmail.get().getId(), id)) {
            log.error("Error when updating user - e-mail already registered - request: {}", request);
            throw new InvalidEmailException(request.getEmail());
        }

        CustomerEntity customerToUpdate = CustomerEntity.builder().customerRequest(request).build();
        customerToUpdate.setId(id);

        CustomerEntity updatedCustomer = customerRepository.save(customerToUpdate);

        CustomerResponseDTO response = createCustomerResponse(updatedCustomer);

        log.info("Customer updated - response: {}", response);
        return response;
    }

    public void deleteCustomer(Integer id) {
        log.info("Looking for customer to delete - id: {}", id);

        getCustomerEntity(id);
        customerRepository.deleteById(id);

        log.info("Customer deleted - id: {}", id);
    }

    private CustomerResponseDTO createCustomerResponse(CustomerEntity createdCustomer) {
        return CustomerResponseDTO.builder().customerEntity(createdCustomer).build();
    }

    private CustomerEntity getCustomerEntity(Integer id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            log.error("Error - customer not found - id: {}", id);
            throw new CustomerNotFoundException(id);
        }

        return customer.get();
    }

}
