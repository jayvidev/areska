package com.areska.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "description", "price", "originalPrice", "mainImage", "stock", "badge", "category",
        "images", "colors", "sizes", "features", "createdAt", "updatedAt" })
public record ProductDetailResponse(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        BigDecimal originalPrice,
        String mainImage,
        Integer stock,
        String badge,

        @JsonIgnore Integer categoryId,
        @JsonIgnore String categoryName,

        List<ProductImageItem> images,
        List<ProductColorItem> colors,
        List<ProductSizeItem> sizes,
        List<ProductFeatureItem> features,

        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    @JsonGetter("category")
    public Category getCategory() {
        return new Category(categoryId, categoryName);
    }

    public ProductDetailResponse withImagesColorsAndSizesAndFeatures(List<ProductImageItem> images,
            List<ProductColorItem> colors,
            List<ProductSizeItem> sizes, List<ProductFeatureItem> features) {
        return new ProductDetailResponse(
                id,
                name,
                description,
                price,
                originalPrice,
                mainImage,
                stock,
                badge,
                categoryId,
                categoryName,
                images,
                colors,
                sizes,
                features,
                createdAt,
                updatedAt);
    }

    public record Category(
            Integer id,
            String name) {
    }

    public record ProductImageItem(
            String imageUrl,
            Integer displayOrder) {
    }

    public record ProductColorItem(
            String name,
            String hexValue,
            Integer displayOrder) {
    }

    public record ProductSizeItem(
            String sizeName,
            Integer displayOrder) {
    }

    public record ProductFeatureItem(
            String featureText,
            Integer displayOrder) {
    }
}