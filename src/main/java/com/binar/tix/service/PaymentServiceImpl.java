package com.binar.tix.service;

import com.binar.tix.entities.Payment;
import com.binar.tix.payload.ReqCreatePayment;
import com.binar.tix.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findByPaymentId(Integer paymentId){
        return paymentRepository.findById(paymentId);
    }

    @Override
    public Payment savePayment(ReqCreatePayment payment) {
        Payment payment1 = new Payment();
        payment1.setPaymentMethod(payment.getPaymentMethod());
        payment1.setStatus(payment.getStatus());
        return paymentRepository.save(payment1);
    }

    @Override
    public Boolean updatePayment(Integer paymentId, ReqCreatePayment payment) {
        Optional<Payment> payments = paymentRepository.findById(paymentId);
        if (payments.isPresent()) {
            Payment payment1 = payments.get();
            payment1.setPaymentMethod(payment.getPaymentMethod());
            payment1.setStatus(payment.getStatus());
            paymentRepository.saveAndFlush(payment1);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deletePayment(Integer paymentId) {
        Optional<Payment> payments = paymentRepository.findById(paymentId);
        if (payments.isPresent()) {
            Payment payment1= payments.get();
            paymentRepository.delete(payment1);
            return true;
        } else {
            return false;
        }
    }
}
