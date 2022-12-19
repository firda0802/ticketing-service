package com.binar.tix.service;

import com.binar.tix.entities.Payment;
import com.binar.tix.payload.ReqUpdatePayment;
import com.binar.tix.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public void createPayment(ReqUpdatePayment req) {
        Payment newPayment = new Payment();
        newPayment.setPaymentMethod(req.getPaymentMethod());
        newPayment.setStatus(Boolean.TRUE);
        paymentRepository.saveAndFlush(newPayment);
    }

    @Override
    public void disablePayment(int idPayment) {
       paymentRepository.disablePayment(idPayment);
    }

    @Override
    public Boolean updatePayment(int idPayment, ReqUpdatePayment req) {
        Optional<Payment> cek = paymentRepository.findById(idPayment);
        if(cek.isPresent()){
            Payment p = cek.get();
            p.setPaymentMethod(req.getPaymentMethod());
            paymentRepository.saveAndFlush(p);
            return true;
        }else{
            return false;
        }
    }
}
