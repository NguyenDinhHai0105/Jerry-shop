package org.jerry.product.service;

import lombok.RequiredArgsConstructor;
import org.jerry.product.dto.product.ProductPurchaseRequest;
import org.jerry.product.dto.product.ProductPurchaseResponse;
import org.jerry.product.dto.product.ProductRequest;
import org.jerry.product.dto.product.ProductResponse;
import org.jerry.product.entity.Product;
import org.jerry.product.exception.ProductPurchaseException;
import org.jerry.product.exception.ResourceNotFoundException;
import org.jerry.product.repository.CategoryRepository;
import org.jerry.product.repository.ProductRepository;
import org.jerry.product.ulti.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse create(ProductRequest request) {
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found with id: " + request.getCategoryId());
        }
        Product p = ProductMapper.toEntity(request);
        p.setId(UUID.randomUUID());
        p = productRepository.save(p);
        return ProductMapper.toResponse(p);
    }

    public ProductResponse update(UUID id, ProductRequest request) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found with id: " + request.getCategoryId());
        }
        ProductMapper.updateEntity(p, request);
        p = productRepository.save(p);
        return ProductMapper.toResponse(p);
    }

    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public ProductResponse getById(UUID id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ProductMapper.toResponse(p);
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductPurchaseResponse> purchaseProducts(UUID orderId, List<ProductPurchaseRequest> requests) {
        var productIds = requests
                .stream()
                .map(ProductPurchaseRequest::getProductId)
                .toList();

        var availableProducts = productRepository.findAllById(productIds);
        if (productIds.size() != availableProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        var purchaseProduct = new ArrayList<ProductPurchaseResponse>();

        for (var request : requests) {
            var product = availableProducts
                    .stream()
                    .filter(p -> p.getId().equals(request.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new ProductPurchaseException("Product not found with id: " + request.getProductId()));

            if (product.getAvailableQuantity() < request.getQuantity()) {
                throw new ProductPurchaseException("Insufficient stock for product id: " + request.getProductId());
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - request.getQuantity());
            productRepository.save(product);
            purchaseProduct.add(ProductMapper.toProductPurchaseResponse(product, request.getQuantity()));
        }
        return purchaseProduct;
    }
}
