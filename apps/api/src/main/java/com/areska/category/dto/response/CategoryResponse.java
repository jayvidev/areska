package com.areska.category.dto.response;

import java.time.LocalDateTime;

public record CategoryResponse(
    Integer id,
    String name,
    String slug,
    String description,
    LocalDateTime createdAt
) {}
