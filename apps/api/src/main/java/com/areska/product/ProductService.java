package com.areska.product;

import com.areska.category.CategoryService;
import com.areska.product.dto.request.ProductRequest;
import com.areska.product.dto.response.ProductAdminListResponse;
import com.areska.product.dto.response.ProductDetailResponse;
import com.areska.product.dto.response.ProductPublicListResponse;
import com.areska.product.model.Product;
import com.areska.product.repository.ProductColorRepository;
import com.areska.product.repository.ProductFeatureRepository;
import com.areska.product.repository.ProductImageRepository;
import com.areska.product.repository.ProductRepository;
import com.areska.product.repository.ProductSizeRepository;
import com.areska.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductColorRepository productColorRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductFeatureRepository productFeatureRepository;

    private final CategoryService categoryService;

    public List<ProductAdminListResponse> getAdminList() {
        return productRepository.findAdminList();
    }

    public List<ProductPublicListResponse> getPublicList() {
        return productRepository.findPublicList();
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public ProductDetailResponse getDetailById(Integer id) {
        ProductDetailResponse base = productRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        return base.withImagesColorsAndSizesAndFeatures(
                productImageRepository.findProductImageItemsByProductId(id),
                productColorRepository.findProductColorItemsByProductId(id),
                productSizeRepository.findProductSizeItemsByProductId(id),
                productFeatureRepository.findProductFeatureItemsByProductId(id));
    }

    @Transactional
    public ProductAdminListResponse create(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        product.setCategory(categoryService.findById(request.categoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found with ID: " + request.categoryId())));

        Product saved = productRepository.save(product);

        return toAdminListResponse(saved);
    }

    @Transactional
    public ProductAdminListResponse update(Integer id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        product.setCategory(categoryService.findById(request.categoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found with ID: " + request.categoryId())));

        Product updated = productRepository.save(product);

        return toAdminListResponse(updated);
    }

    private ProductAdminListResponse toAdminListResponse(Product product) {
        return new ProductAdminListResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt());
    }
}
