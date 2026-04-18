package com.areska.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	List<Payment> findAllByOrder_Id(Integer orderId);
}
