package com.areska.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "price", "originalPrice", "mainImage", "badge", "category", "createdAt" })
public record ProductPublicListResponse(
        Integer id,
        String name,
        BigDecimal price,
        BigDecimal originalPrice,
        String mainImage,
        String badge,

        @JsonIgnore Integer categoryId,
        @JsonIgnore String categoryName,

        LocalDateTime createdAt) {

    @JsonGetter("category")
    public Category getCategory() {
        return new Category(categoryId, categoryName);
    }

    public record Category(
            Integer id,
            String name) {
    }
}