package com.example.customer.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Integer age;

    @NotBlank
    private String email;

    @Valid
    @NotNull
    private AddressRequestDTO address;

}