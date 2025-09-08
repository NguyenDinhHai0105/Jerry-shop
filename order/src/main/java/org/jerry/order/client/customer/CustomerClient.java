package org.jerry.order.client.customer;

import org.jerry.order.exception.BusinessException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(
        name = "customer-service",
        url = "${feign.client.config.customer.url:_}"
)
public interface CustomerClient {

    @GetMapping("/{customerId}")
    Optional<CustomerResponse> getCustomerById(@PathVariable("customerId") UUID customerId) throws BusinessException;

}
