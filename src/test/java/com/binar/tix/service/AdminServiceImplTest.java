package com.binar.tix.service;

import com.binar.tix.entities.Payment;
import com.binar.tix.payload.ReqUpdatePayment;
import com.binar.tix.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdminServiceImplTest {

    @InjectMocks
    AdminService adminService = new AdminServiceImpl();

    @Mock
    PaymentRepository paymentRepository;

    Payment payment = new Payment();

    @BeforeEach
    void setUp() {
        payment.setPaymentMethod("DANA");
        payment.setStatus(Boolean.TRUE);
        paymentRepository.saveAndFlush(payment);
    }

    @Test
    void createPayment() {
        ReqUpdatePayment req = new ReqUpdatePayment();
        req.setPaymentMethod("BCA");
        payment.setPaymentMethod(req.getPaymentMethod());
        adminService.createPayment(req);
        doReturn(payment).when(paymentRepository).save(payment);
        assertEquals(payment, paymentRepository.save(payment));
    }

    @Test
    void disablePayment() {
        adminService.disablePayment(1);
        doNothing().when(paymentRepository).disablePayment(1);
        verify(paymentRepository).disablePayment(1);
    }

    @Test
    void updatePayment() {
        ReqUpdatePayment req = new ReqUpdatePayment();
        req.setPaymentMethod("Mandiri");
        adminService.updatePayment(1, req);
        doReturn(Optional.of(payment)).when(paymentRepository).findById(1);
        verify(paymentRepository).findById(1);
        payment.setPaymentMethod(req.getPaymentMethod());
        verify(paymentRepository).saveAndFlush(payment);
    }
}