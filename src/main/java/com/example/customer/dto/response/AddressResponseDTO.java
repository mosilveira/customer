package com.example.customer.dto.response;

import com.example.customer.entities.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDTO {

    private Integer id;
    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public static class AddressResponseDTOBuilder {

        public AddressResponseDTOBuilder addressEntity(AddressEntity address) {
            this.id = address.getId();
            this.street = address.getStreet();
            this.number = address.getNumber();
            this.district = address.getDistrict();
            this.city = address.getCity();
            this.state = address.getState();
            this.zipCode = address.getZipCode();
            this.country = address.getCountry();

            return this;
        }

    }

}