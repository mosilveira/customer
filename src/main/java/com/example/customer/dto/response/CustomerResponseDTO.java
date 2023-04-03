package com.example.customer.dto.response;

import com.example.customer.entities.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDTO {

    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private AddressResponseDTO address;

    public static class CustomerResponseDTOBuilder {

        public CustomerResponseDTOBuilder customerEntity(CustomerEntity customer) {
            this.id = customer.getId();
            this.name = customer.getName();
            this.age = customer.getAge();
            this.email = customer.getEmail();
            this.address = AddressResponseDTO.builder().addressEntity(customer.getAddress()).build();

            return this;
        }

    }

}