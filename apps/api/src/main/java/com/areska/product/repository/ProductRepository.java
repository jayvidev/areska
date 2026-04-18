package com.areska.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.areska.product.dto.response.ProductPublicListResponse;
import com.areska.product.dto.response.ProductAdminListResponse;
import com.areska.product.dto.response.ProductDetailResponse;
import com.areska.product.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("""
        SELECT 
            p.id AS id,
            p.name AS name,
            p.description AS description,
            p.price AS price,
            p.stock AS stock,

            c.id AS categoryId,
            c.name AS categoryName,

            p.createdAt AS createdAt
        FROM Product p
        JOIN p.category c
        ORDER BY p.id DESC
    """)
    List<ProductAdminListResponse> findAdminList();

    @Query("""
        SELECT 
            p.id AS id,
            p.name AS name,
            p.price AS price,
            p.originalPrice AS originalPrice,
            p.mainImage AS mainImage,
            p.badge AS badge,

            c.id AS categoryId,
            c.name AS categoryName,

            p.createdAt AS createdAt
        FROM Product p
        JOIN p.category c
    """)
    List<ProductPublicListResponse> findPublicList();

    @Query("""
        SELECT 
            p.id AS id,
            p.name AS name,
            p.description AS description,
            p.price AS price,
            p.originalPrice AS originalPrice,
            p.mainImage AS mainImage,
            p.stock AS stock,
            p.badge AS badge,

            c.id AS categoryId,
            c.name AS categoryName,

            NULL AS images,
            NULL AS colors,
            NULL AS sizes,
            NULL AS features,

            p.createdAt AS createdAt,
            p.updatedAt AS updatedAt
        FROM Product p
        JOIN p.category c
        WHERE p.id = :id
    """)
    Optional<ProductDetailResponse> findDetailById(Integer id);
}
