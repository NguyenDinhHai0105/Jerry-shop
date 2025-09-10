package org.jerry.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jerry.product.dto.product.ProductPurchaseRequest;
import org.jerry.product.dto.product.ProductPurchaseResponse;
import org.jerry.product.dto.product.ProductRequest;
import org.jerry.product.dto.product.ProductResponse;
import org.jerry.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse created = productService.create(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        ProductResponse updated = productService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
        ProductResponse p = productService.getById(id);
        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        List<ProductResponse> list = productService.getAll();
        return ResponseEntity.ok(list);
    }

//    @PostMapping("/purchase")
//    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> requests) {
//        return ResponseEntity.ok(productService.purchaseProducts(requests));
//    }
}

