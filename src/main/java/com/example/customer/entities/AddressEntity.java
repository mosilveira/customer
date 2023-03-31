package com.example.customer.entities;

import com.example.customer.dto.request.AddressRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
    @SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "STREET")
    private String street;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "COUNTRY")
    private String country;

    public static class AddressEntityBuilder {

        public AddressEntityBuilder addressRequest(AddressRequestDTO request) {
            this.street = request.getStreet();
            this.number = request.getNumber();
            this.district = request.getDistrict();
            this.city = request.getCity();
            this.state = request.getState();
            this.zipCode = request.getZipCode();
            this.country = request.getCountry();

            return this;
        }

    }

}
