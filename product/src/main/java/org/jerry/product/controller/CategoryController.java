package org.jerry.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jerry.product.dto.category.CategoryRequest;
import org.jerry.product.dto.category.CategoryResponse;
import org.jerry.product.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(201).body(categoryService.create(request));
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("category-id") UUID categoryId, @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(categoryId, request));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<String> delete(@PathVariable("category-id") UUID categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable("category-id") UUID categoryId) {
        return ResponseEntity.ok(categoryService.getById(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}

