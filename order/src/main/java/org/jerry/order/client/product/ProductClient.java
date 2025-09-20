package org.jerry.order.client.product;

import org.jerry.common.dto.request.PurchaseRequest;
import org.jerry.common.dto.product.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "product-service",
        url = "${feign.client.config.product.url:_}"
)
public interface ProductClient {
    @PostMapping("/purchase")
    List<PurchaseResponse> purchaseProducts(@RequestBody List<PurchaseRequest> requests);
}
