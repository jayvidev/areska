package com.areska.orderDetail.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailUpdateRequest {
	@Positive(message = "Product ID must be a positive number")
    private Integer productId;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}