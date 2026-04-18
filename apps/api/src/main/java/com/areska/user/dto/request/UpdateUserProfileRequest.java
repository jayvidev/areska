package com.areska.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(
    @NotBlank(message = "First name is required")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    String lastName,

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^$|^9\\d{8}$", message = "Phone number must have 9 digits and start with 9")
    String phone,

    @Size(max = 255, message = "Address must not exceed 255 characters")
    String address
) {}
