package org.jerry.ecommerce.service.util;

import org.jerry.ecommerce.entity.Address;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressMapper {
    public Address toAddress(Address address) {
        if (address.getId() == null) {
            UUID id = UUID.randomUUID();
            return Address.builder()
                    .id(id)
                    .street(address.getStreet())
                    .houseNumber(address.getHouseNumber())
                    .zipCode(address.getZipCode())
                    .build();
        } else  {
            return address;
        }

    }
}
