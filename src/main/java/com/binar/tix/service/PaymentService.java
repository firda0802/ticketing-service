package com.binar.tix.service;

import com.binar.tix.entities.Payment;
import com.binar.tix.payload.ReqCreatePayment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<Payment> findAllPayment();

    Optional<Payment> findByPaymentId(Integer paymentId);

    Payment savePayment(ReqCreatePayment payment);

    Boolean updatePayment(Integer paymentId, ReqCreatePayment payment);

    Boolean deletePayment(Integer paymentId);

}
