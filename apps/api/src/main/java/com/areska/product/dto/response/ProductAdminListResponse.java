package com.areska.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "description", "price", "stock", "category", "createdAt" })
public record ProductAdminListResponse(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,

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
