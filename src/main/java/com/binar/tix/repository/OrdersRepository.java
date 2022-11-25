package com.binar.tix.repository;

import com.binar.tix.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer>{
    Optional<Orders> findByEmailIgnoreCase(String email);
}
