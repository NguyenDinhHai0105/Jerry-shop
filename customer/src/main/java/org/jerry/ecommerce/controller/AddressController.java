package org.jerry.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.jerry.ecommerce.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
}
