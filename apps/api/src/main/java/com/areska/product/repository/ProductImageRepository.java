package com.areska.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.areska.product.dto.response.ProductDetailResponse;
import com.areska.product.model.ProductImage;

public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {
    @Query("""
        SELECT 
            pi.imageUrl AS imageUrl,
            pi.displayOrder AS displayOrder
        FROM ProductImage pi
        WHERE pi.product.id = :id
    """)
    List<ProductDetailResponse.ProductImageItem> findProductImageItemsByProductId(Integer id);
}
