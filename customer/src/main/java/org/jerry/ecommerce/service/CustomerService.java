package org.jerry.ecommerce.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.jerry.ecommerce.dto.request.CustomerRequest;
import org.jerry.ecommerce.dto.response.CustomerResponse;
import org.jerry.ecommerce.entity.Customer;
import org.jerry.ecommerce.entity.QAddress;
import org.jerry.ecommerce.entity.QCustomer;
import org.jerry.ecommerce.exception.NotFoundException;
import org.jerry.ecommerce.repository.AddressRepository;
import org.jerry.ecommerce.repository.CustomerRepository;
import org.jerry.ecommerce.service.util.AddressMapper;
import org.jerry.ecommerce.service.util.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final JPAQueryFactory queryFactory;

    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository, CustomerMapper customerMapper, AddressMapper addressMapper, EntityManager entityManager) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public void createCustomer(@Valid CustomerRequest customerRequest) {
        var address = addressRepository.save(addressMapper.toAddress(customerRequest.address()));
        Customer customer = customerMapper.toCustomer(customerRequest);
        customer.setAddressId(address.getId());
        customerRepository.save(customer);
    }

    public void updateCustomer(@Valid CustomerRequest customerRequest) {
        customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerRequest.id()));
        addressRepository.findById(customerRequest.address().getId())
                .orElseThrow(() -> new NotFoundException("Address not found with id: " + customerRequest.address().getId()));

        var customer = customerMapper.toCustomer(customerRequest);
        var address = addressMapper.toAddress(customerRequest.address());
        customerRepository.save(customer);
        addressRepository.save(address);
    }

    public List<CustomerResponse> getAllCustomersWithAddress() {
        QCustomer customer = QCustomer.customer;
        QAddress address = QAddress.address;
        List<Tuple> tuples = queryFactory
                .select(customer.id, customer.firstName, customer.lastName, customer.email, address)
                .from(customer)
                .leftJoin(address).on(customer.addressId.eq(address.id))
                .fetch();
        List<CustomerResponse> responses = new ArrayList<>();
        if (tuples.isEmpty()) {
            return responses;
        }
        for (Tuple tuple : tuples) {
            responses.add(new CustomerResponse(
                    tuple.get(customer.id),
                    tuple.get(customer.firstName),
                    tuple.get(customer.lastName),
                    tuple.get(customer.email),
                    tuple.get(address)
            ));
        }
        return responses;
    }

    public Boolean isCustomerExists(UUID customerId) {
        return customerRepository.existsById(customerId);
    }

    public CustomerResponse getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    var address = addressRepository.findById(customer.getAddressId())
                            .orElseThrow(() -> new NotFoundException("Address not found with id: " + customer.getAddressId()));
                    return new CustomerResponse(
                            customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail(),
                            address
                    );
                })
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));
    }

    public void deleteCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));
        // Delete associated address first
        addressRepository.deleteById(customer.getAddressId());
        // Delete the customer
        customerRepository.deleteById(customerId);
    }
}
