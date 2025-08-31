package org.jerry.product.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductRequest {
    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "availableQuantity is required")
    @PositiveOrZero(message = "availableQuantity must be zero or positive")
    private Double availableQuantity;

    @NotNull(message = "price is required")
    private BigDecimal price;

    @NotNull(message = "categoryId is required")
    private UUID categoryId;
}

