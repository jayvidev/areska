package com.areska.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.areska.shared.exception.ResourceNotFoundException;
import com.areska.user.dto.request.FirebaseUserRequest;
import com.areska.user.dto.request.UpdateUserProfileRequest;
import com.areska.user.dto.response.UserDetailResponse;
import com.areska.user.dto.response.UserListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public List<UserListResponse> getList() {
        return userRepository.findList();
    }

    public UserDetailResponse getDetailById(Integer id) {
        return userRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public UserDetailResponse getDetailByFirebaseUid(String firebaseUid) {
        return userRepository.findDetailByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Firebase UID: " + firebaseUid));
    }

    @Transactional
    public UserDetailResponse syncWithFirebase(FirebaseUserRequest request) {
        Optional<User> existingUser = Optional.empty();

        if (request.firebaseUid() != null && !request.firebaseUid().isBlank()) {
            existingUser = userRepository.findByFirebaseUid(request.firebaseUid());
        }

        if (existingUser.isEmpty() && request.email() != null && !request.email().isBlank()) {
            existingUser = userRepository.findByEmailIgnoreCase(request.email());
        }

        User user = existingUser.orElseGet(User::new);

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setAuthProvider(request.authProvider());
        user.setFirebaseUid(request.firebaseUid());
        user.setEmailVerified(request.emailVerified());
        user.setPhotoUrl(request.photoUrl());

        User saved = userRepository.save(user);
        return toDetailResponse(saved);
    }

    @Transactional
    public UserDetailResponse updateEmail(String firebaseUid, String email) {
        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with firebase UID: " + firebaseUid));

        user.setEmail(email);

        User updated = userRepository.save(user);
        return toDetailResponse(updated);
    }

    @Transactional
    public UserDetailResponse updateProfile(String firebaseUid, UpdateUserProfileRequest request) {
        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with firebase UID: " + firebaseUid));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        user.setAddress(request.address());

        User updated = userRepository.save(user);
        return toDetailResponse(updated);
    }

    @Transactional
    public UserDetailResponse updatePhoto(String firebaseUid, String photoUrl) {
        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with firebase UID: " + firebaseUid));

        user.setPhotoUrl(photoUrl);

        User updated = userRepository.save(user);
        return toDetailResponse(updated);
    }

    private UserDetailResponse toDetailResponse(User user) {
        return new UserDetailResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getFirebaseUid(),
                user.getAuthProvider(),
                user.getEmailVerified(),
                user.getPhotoUrl(),
                user.getCreatedAt());
    }
}
