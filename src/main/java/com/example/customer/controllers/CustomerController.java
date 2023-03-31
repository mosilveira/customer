package com.example.customer.controllers;

import com.example.customer.dto.request.CustomerRequestDTO;
import com.example.customer.dto.response.CustomerResponseDTO;
import com.example.customer.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/v1/customers")
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody @Valid CustomerRequestDTO request) {
        log.info("Request to create customer received - request: {}", request);
        CustomerResponseDTO response = customerService.create(request);

        log.info("Successfully created customer - response: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/v1/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable("id") Integer id) {
        log.info("Request to get a customer by id received - id: {}", id);
        CustomerResponseDTO response = customerService.findById(id);

        log.info("Search performed successfully - response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/v1/customers")
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        log.info("Request to get a list of customers received");
        List<CustomerResponseDTO> response = customerService.findAll();

        String ids = response.stream().map(customer -> String.valueOf(customer.getId())).collect(Collectors.joining(","));
        log.info("Search performed successfully - ids: {}", ids);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/v1/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid CustomerRequestDTO request) {
        log.info("Request to update customer received - id: {}, request: {}", id, request);
        CustomerResponseDTO response = customerService.update(id, request);

        log.info("Successfully updated customer - response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/v1/customers/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        log.info("Request to remove customer received - id: {}", id);
        customerService.delete(id);

        log.info("Successfully removed customer - id: {}", id);
        return ResponseEntity.noContent().build();
    }

}
