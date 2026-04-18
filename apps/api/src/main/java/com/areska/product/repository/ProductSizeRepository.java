package com.areska.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.areska.product.dto.response.ProductDetailResponse;
import com.areska.product.model.ProductSize;

public interface ProductSizeRepository extends CrudRepository<ProductSize, Integer> {
    @Query("""
        SELECT 
            ps.sizeName AS sizeName,
            ps.displayOrder AS displayOrder
        FROM ProductSize ps
        WHERE ps.product.id = :id
    """)
    List<ProductDetailResponse.ProductSizeItem> findProductSizeItemsByProductId(Integer id);
}
