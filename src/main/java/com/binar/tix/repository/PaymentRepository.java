package com.binar.tix.repository;

import com.binar.tix.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByStatus(Boolean status);
}