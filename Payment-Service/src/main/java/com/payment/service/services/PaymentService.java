package com.payment.service.services;

import com.payment.service.entities.Payment;
import com.payment.service.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public ResponseEntity<String> createPayment(Payment payment) {
        paymentRepository.save(payment);
        return ResponseEntity.ok().body("Payment Success!");
    }
}
