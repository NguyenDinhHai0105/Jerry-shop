package org.jerry.product.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductPurchaseResponse {
    private UUID productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Double quantity;
}
