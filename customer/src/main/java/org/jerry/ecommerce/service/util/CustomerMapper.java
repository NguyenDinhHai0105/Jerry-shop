package org.jerry.ecommerce.service.util;

import jakarta.validation.Valid;
import org.jerry.ecommerce.dto.request.CustomerRequest;
import org.jerry.ecommerce.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerMapper {

    public Customer toCustomer(@Valid CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .addressId(customerRequest.address().getId())
                .build();
        if (customerRequest.id() == null) {
            UUID uuid = UUID.randomUUID();
            customer.setId(uuid);
        } else {
            customer.setId(customerRequest.id());
        }
        return customer;
    }
}
