package com.binar.tix.repository;


import com.binar.tix.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
