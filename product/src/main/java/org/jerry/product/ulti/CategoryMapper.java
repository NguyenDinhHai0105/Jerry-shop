package org.jerry.product.ulti;

import lombok.experimental.UtilityClass;
import org.jerry.product.dto.category.CategoryRequest;
import org.jerry.product.dto.category.CategoryResponse;
import org.jerry.product.entity.Category;

@UtilityClass
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        if (request == null) return null;
        Category c = new Category();
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        return c;
    }

    public void updateEntity(Category entity, CategoryRequest request) {
        if (entity == null || request == null) return;
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

    public CategoryResponse toResponse(Category entity) {
        if (entity == null) return null;
        CategoryResponse r = new CategoryResponse();
        r.setId(entity.getId());
        r.setName(entity.getName());
        r.setDescription(entity.getDescription());
        return r;
    }
}

