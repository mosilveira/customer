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

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDTO create(CustomerRequestDTO request) {
        Optional<CustomerEntity> customerFound = customerRepository.findByEmail(request.getEmail());

        if (customerFound.isPresent()) {
            throw new InvalidEmailException(request.getEmail());
        }

        CustomerEntity customerToCreate = CustomerEntity.builder().customerRequest(request).build();
        CustomerEntity createdCustomer = customerRepository.save(customerToCreate);

        return createCustomerResponse(createdCustomer);
    }

    public CustomerResponseDTO findById(Integer id) {
        Optional<CustomerEntity> optional = getCustomerEntity(id);

        CustomerEntity customer = optional.get();

        return createCustomerResponse(customer);
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(this::createCustomerResponse)
                .toList();
    }

    public CustomerResponseDTO update(Integer id, CustomerRequestDTO request) {
        getCustomerEntity(id);

        Optional<CustomerEntity> customerByEmail = customerRepository.findByEmail(request.getEmail());

        if (customerByEmail.isPresent() && !Objects.equals(customerByEmail.get().getId(), id)) {
            throw new InvalidEmailException(request.getEmail());
        }

        CustomerEntity customerToUpdate = CustomerEntity.builder().customerRequest(request).build();
        customerToUpdate.setId(id);

        CustomerEntity updatedCustomer = customerRepository.save(customerToUpdate);

        return createCustomerResponse(updatedCustomer);
    }

    public void delete(Integer id) {
        getCustomerEntity(id);
        customerRepository.deleteById(id);
    }

    private CustomerResponseDTO createCustomerResponse(CustomerEntity createdCustomer) {
        return CustomerResponseDTO.builder().customerEntity(createdCustomer).build();
    }

    private Optional<CustomerEntity> getCustomerEntity(Integer id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }

        return customer;
    }

}
