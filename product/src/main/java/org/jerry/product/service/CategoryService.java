package org.jerry.product.service;

import lombok.RequiredArgsConstructor;
import org.jerry.product.dto.category.CategoryRequest;
import org.jerry.product.dto.category.CategoryResponse;
import org.jerry.product.entity.Category;
import org.jerry.product.exception.ResourceNotFoundException;
import org.jerry.product.ulti.CategoryMapper;
import org.jerry.product.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse create(CategoryRequest request) {
        Category category = CategoryMapper.toEntity(request);
        category.setId(UUID.randomUUID());
        category = categoryRepository.save(category);
        return CategoryMapper.toResponse(category);
    }

    public CategoryResponse update(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        CategoryMapper.updateEntity(category, request);
        category = categoryRepository.save(category);
        return CategoryMapper.toResponse(category);
    }

    public void delete(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public CategoryResponse getById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return CategoryMapper.toResponse(category);
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
