package com.example.customer.entities;

import com.example.customer.dto.request.CustomerRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ")
    @SequenceGenerator(name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ADDRESS_ID")
    private AddressEntity address;

    public static class CustomerEntityBuilder {

        public CustomerEntityBuilder customerRequest(CustomerRequestDTO request) {
            this.name = request.getName();
            this.age = request.getAge();
            this.email = request.getEmail();
            this.address = AddressEntity.builder().addressRequest(request.getAddress()).build();

            return this;
        }

    }

}
