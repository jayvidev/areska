package com.areska.user.dto.response;

import java.time.LocalDateTime;

public record UserDetailResponse(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String address,
        String firebaseUid,
        String authProvider,
        Boolean emailVerified,
        String photoUrl,
        LocalDateTime createdAt) {
}
