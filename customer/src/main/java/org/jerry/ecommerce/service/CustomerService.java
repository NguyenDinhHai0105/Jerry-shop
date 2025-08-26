package org.jerry.ecommerce.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jerry.ecommerce.dto.request.CustomerRequest;
import org.jerry.ecommerce.entity.Customer;
import org.jerry.ecommerce.repository.AddressRepository;
import org.jerry.ecommerce.repository.CustomerRepository;
import org.jerry.ecommerce.service.util.AddressMapper;
import org.jerry.ecommerce.service.util.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    public String createCustomer(@Valid CustomerRequest customerRequest) {
        var address = addressRepository.save(addressMapper.toAddress(customerRequest.address()));
        Customer customer = customerMapper.toCustomer(customerRequest);
        customer.setAddressId(address.getId());
        customerRepository.save(customer);
        return customer.getId().toString();
    }
}
