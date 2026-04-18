package com.areska.payment.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentCreateRequest {

    private Integer orderId;
    private String method; 
    private BigDecimal amount;
}
