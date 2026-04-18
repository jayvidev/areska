package com.areska.user.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateUserPhotoRequest(
    @Size(max = 500, message = "Photo URL must not exceed 500 characters")
    String photoUrl
) {}
