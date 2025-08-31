package org.jerry.product.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductResponse {
    private UUID id;
    private String name;
    private String description;
    private Double availableQuantity;
    private BigDecimal price;
    private UUID categoryId;
}

