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

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/v1/customers")
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody @Valid CustomerRequestDTO request) {
        CustomerResponseDTO response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/v1/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable("id") Integer id) {
        CustomerResponseDTO response = customerService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/v1/customers")
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        List<CustomerResponseDTO> response = customerService.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/v1/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable("id") Integer id, @RequestBody @Valid CustomerRequestDTO request) {
        CustomerResponseDTO response = customerService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/v1/customers/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
