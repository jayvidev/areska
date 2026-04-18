package com.areska.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.areska.product.dto.response.ProductDetailResponse;
import com.areska.product.model.ProductFeature;

public interface ProductFeatureRepository extends CrudRepository<ProductFeature, Integer> {
    @Query("""
        SELECT 
            pf.featureText AS featureText,
            pf.displayOrder AS displayOrder
        FROM ProductFeature pf
        WHERE pf.product.id = :id
    """)
    List<ProductDetailResponse.ProductFeatureItem> findProductFeatureItemsByProductId(Integer id);
}
