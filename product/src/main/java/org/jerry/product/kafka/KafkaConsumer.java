package org.jerry.product.kafka;

import lombok.RequiredArgsConstructor;
import org.jerry.common.dto.order.OrderCreateMessage;
import org.jerry.product.dto.product.ProductPurchaseRequest;
import org.jerry.product.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ProductService productService;

    @KafkaListener(topics = "${topic.order.create}", groupId = "product-service-group")
    public void consumeOrderCreated(OrderCreateMessage message) {
        List<ProductPurchaseRequest> requests = message.getProducts().stream().map(
                p -> ProductPurchaseRequest.builder()
                        .productId(p.getProductId())
                        .quantity(p.getQuantity())
                        .build()
        ).toList();
        productService.purchaseProducts(message.getOrderId(), requests);
    }
}
