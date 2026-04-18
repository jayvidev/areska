package com.areska.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.areska.product.dto.response.ProductDetailResponse;
import com.areska.product.model.ProductColor;

public interface ProductColorRepository extends CrudRepository<ProductColor, Integer> {
    @Query("""
        SELECT 
            pc.name AS name,
            pc.hexValue AS hexValue,
            pc.displayOrder AS displayOrder
        FROM ProductColor pc
        WHERE pc.product.id = :id
    """)
    List<ProductDetailResponse.ProductColorItem> findProductColorItemsByProductId(Integer id);
}
