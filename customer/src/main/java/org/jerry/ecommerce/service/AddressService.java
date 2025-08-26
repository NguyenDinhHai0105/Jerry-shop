package org.jerry.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.jerry.ecommerce.entity.Address;
import org.jerry.ecommerce.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;
}
