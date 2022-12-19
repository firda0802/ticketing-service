package com.binar.tix.repository;

import com.binar.tix.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Payment a set a.status = false where a.paymentId =:paymentId")
    void disablePayment(@Param("paymentId") int paymentId);

    List<Payment> findByStatus(Boolean status);
}