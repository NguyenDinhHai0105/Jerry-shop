package org.jerry.product.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductPurchaseRequest {
    @NotNull(message = "Product is mandatory")
    private UUID productId;
    @NotNull(message = "Quantity is mandatory")
    private Double quantity;
}
