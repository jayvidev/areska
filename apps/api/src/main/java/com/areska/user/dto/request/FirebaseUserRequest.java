package com.areska.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FirebaseUserRequest(
    @NotBlank(message = "First name is required")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    String lastName,

    @NotBlank(message = "Email is required")
    @Email
    String email,

    @NotBlank(message = "Firebase UID is required")
    String firebaseUid,

    @NotBlank(message = "Auth provider is required")
    String authProvider,

    boolean emailVerified,

    String photoUrl
) {}
