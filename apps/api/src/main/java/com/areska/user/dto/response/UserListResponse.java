package com.areska.user.dto.response;

public record UserListResponse(
        Integer id,
        String photoUrl,
        String fullName,
        String email,
        String phone,
        String address,
        String authProvider) {
}