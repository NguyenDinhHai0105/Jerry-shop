package org.jerry.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Category {
    @Id
    private UUID id;
    private String name;
    private String description;
}
