package com.areska.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.areska.user.dto.response.UserDetailResponse;
import com.areska.user.dto.response.UserListResponse;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("""
        SELECT 
            u.id AS id,
            u.photoUrl AS photoUrl,
            CONCAT(u.firstName, ' ', u.lastName) AS fullName,
            u.email AS email,
            u.phone AS phone,
            u.address AS address,
            u.authProvider AS authProvider
        FROM User u
        ORDER BY u.id DESC
    """)
    List<UserListResponse> findList();

    @Query("""
        SELECT 
            u.id AS id,
            u.firstName AS firstName,
            u.lastName AS lastName,
            u.email AS email,
            u.phone AS phone,
            u.address AS address,
            u.firebaseUid AS firebaseUid,
            u.authProvider AS authProvider,
            u.emailVerified AS emailVerified,
            u.photoUrl AS photoUrl,
            u.createdAt AS createdAt
        FROM User u
        WHERE u.id = :id
    """)
    Optional<UserDetailResponse> findDetailById(Integer id);

    @Query("""
        SELECT 
            u.id AS id,
            u.firstName AS firstName,
            u.lastName AS lastName,
            u.email AS email,
            u.phone AS phone,
            u.address AS address,
            u.firebaseUid AS firebaseUid,
            u.authProvider AS authProvider,
            u.emailVerified AS emailVerified,
            u.photoUrl AS photoUrl,
            u.createdAt AS createdAt
        FROM User u
        WHERE u.firebaseUid = :firebaseUid
    """)
    Optional<UserDetailResponse> findDetailByFirebaseUid(String firebaseUid);

    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByFirebaseUid(String firebaseUid);
}
