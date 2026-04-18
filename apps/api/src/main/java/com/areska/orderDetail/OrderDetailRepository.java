package com.areska.orderDetail;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer>{
	List<OrderDetail> findByOrder_Id(Integer orderId);
}
