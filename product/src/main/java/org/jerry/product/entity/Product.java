package org.jerry.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class Product {

    @Id
    private UUID id;
    private String name;
    private String description;
    private Double availableQuantity;
    private BigDecimal price;
    private UUID categoryId;

}
