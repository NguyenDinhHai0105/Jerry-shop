package org.jerry.product.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductPurchaseRequest {
    @NotNull(message = "Product is mandatory")
    private UUID productId;
    @NotNull(message = "Quantity is mandatory")
    private Double quantity;
}
