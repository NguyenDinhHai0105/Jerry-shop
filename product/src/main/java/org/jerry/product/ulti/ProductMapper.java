package org.jerry.product.ulti;

import lombok.experimental.UtilityClass;
import org.jerry.product.dto.product.ProductPurchaseResponse;
import org.jerry.product.dto.product.ProductRequest;
import org.jerry.product.dto.product.ProductResponse;
import org.jerry.product.entity.Product;

@UtilityClass
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        if (request == null) return null;
        Product p = new Product();
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        p.setAvailableQuantity(request.getAvailableQuantity());
        p.setPrice(request.getPrice());
        p.setCategoryId(request.getCategoryId());
        return p;
    }

    public void updateEntity(Product entity, ProductRequest request) {
        if (entity == null || request == null) return;
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setAvailableQuantity(request.getAvailableQuantity());
        entity.setPrice(request.getPrice());
        entity.setCategoryId(request.getCategoryId());
    }

    public ProductResponse toResponse(Product entity) {
        if (entity == null) return null;
        ProductResponse r = new ProductResponse();
        r.setId(entity.getId());
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        r.setAvailableQuantity(entity.getAvailableQuantity());
        r.setPrice(entity.getPrice());
        r.setCategoryId(entity.getCategoryId());
        return r;
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product entity, Double quantity) {
        if (entity == null) return null;
        ProductPurchaseResponse r = new ProductPurchaseResponse();
        r.setProductId(entity.getId());
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        r.setPrice(entity.getPrice());
        r.setQuantity(quantity);
        return r;
    }
}

