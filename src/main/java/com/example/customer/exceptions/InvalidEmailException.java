package com.example.customer.exceptions;

import jakarta.validation.constraints.NotBlank;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(@NotBlank String email) {
        super(String.format("%s is already registered", email));
    }

}
