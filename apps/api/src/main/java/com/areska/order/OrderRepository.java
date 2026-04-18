package com.areska.order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> { 
    List<Order> findByUser_Id(Integer userId);
    
    List<Order> findByUser_FirebaseUid(String firebaseUid);
}
