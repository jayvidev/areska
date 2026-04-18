package com.areska.user;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.areska.shared.api.ApiSuccess;
import com.areska.user.dto.request.UpdateUserEmailRequest;
import com.areska.user.dto.request.FirebaseUserRequest;
import com.areska.user.dto.request.UpdateUserProfileRequest;
import com.areska.user.dto.request.UpdateUserPhotoRequest;
import com.areska.user.dto.response.UserDetailResponse;
import com.areska.user.dto.response.UserListResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "List all users")
    public ResponseEntity<ApiSuccess<List<UserListResponse>>> list() {
        List<UserListResponse> users = userService.getList();
        ApiSuccess<List<UserListResponse>> response = new ApiSuccess<>(
                users.isEmpty() ? "No users found" : "Users listed successfully",
                users);

        HttpStatus status = users.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID")
    public ResponseEntity<ApiSuccess<UserDetailResponse>> get(@PathVariable Integer id) {
        UserDetailResponse user = userService.getDetailById(id);
        return ResponseEntity.ok(new ApiSuccess<>("User found", user));
    }

    @GetMapping("/firebase/{firebaseUid}")
    @Operation(summary = "Get a user by Firebase UID")
    public ResponseEntity<ApiSuccess<UserDetailResponse>> getByFirebaseUid(@PathVariable String firebaseUid) {
        UserDetailResponse user = userService.getDetailByFirebaseUid(firebaseUid);
        return ResponseEntity.ok(new ApiSuccess<>("User found", user));
    }

    @PostMapping("/firebase/sync")
    @Operation(summary = "Synchronize user data with Firebase (create or update if needed)")
    public ResponseEntity<ApiSuccess<UserDetailResponse>> syncWithFirebase(
            @Valid @RequestBody FirebaseUserRequest request) {
        UserDetailResponse saved = userService.syncWithFirebase(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiSuccess<>("User successfully synchronized with Firebase", saved));
    }

    @PutMapping("/{firebaseUid}/email")
    @Operation(summary = "Update user email by Firebase UID")
    public ResponseEntity<ApiSuccess<UserDetailResponse>> updateEmail(@PathVariable String firebaseUid,
            @Valid @RequestBody UpdateUserEmailRequest request) {
        UserDetailResponse user = userService.updateEmail(firebaseUid, request.email());
        return ResponseEntity.ok(new ApiSuccess<>("Email updated successfully", user));
    }

    @PutMapping("/{firebaseUid}/profile")
    @Operation(summary = "Update user profile by Firebase UID")
    public ResponseEntity<ApiSuccess<UserDetailResponse>> updateProfile(@PathVariable String firebaseUid,
            @Valid @RequestBody UpdateUserProfileRequest request) {
        UserDetailResponse user = userService.updateProfile(firebaseUid, request);
        return ResponseEntity.ok(new ApiSuccess<>("Profile updated successfully", user));
    }

    @PutMapping("/{firebaseUid}/photo")
    @Operation(summary = "Update user photo by Firebase UID")
    public ResponseEntity<ApiSuccess<UserDetailResponse>> updatePhoto(@PathVariable String firebaseUid,
            @Valid @RequestBody UpdateUserPhotoRequest request) {
        UserDetailResponse user = userService.updatePhoto(firebaseUid, request.photoUrl());
        return ResponseEntity.ok(new ApiSuccess<>("Photo updated successfully", user));
    }
}
