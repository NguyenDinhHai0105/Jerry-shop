package org.jerry.ecommerce.service.util;

import jakarta.validation.Valid;
import org.jerry.ecommerce.dto.request.CustomerRequest;
import org.jerry.ecommerce.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerMapper {

    public Customer toCustomer(@Valid CustomerRequest customerRequest) {
        UUID uuid = UUID.randomUUID();
        return Customer.builder()
                .id(uuid)
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
    }
}
