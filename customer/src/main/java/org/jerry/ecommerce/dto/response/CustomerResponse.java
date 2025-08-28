package org.jerry.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jerry.ecommerce.entity.Address;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}

