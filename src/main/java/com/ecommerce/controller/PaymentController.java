package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payloads.PaymentCreateRequestDTO;
import com.ecommerce.payloads.PaymentCreateResponseDTO;
import com.ecommerce.payloads.PaymentVerifyRequestDTO;
import com.ecommerce.services.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	
	private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //Create Payment Order
    @PostMapping("/create-order")
    public ResponseEntity<PaymentCreateResponseDTO> createPaymentOrder(
            @Valid @RequestBody PaymentCreateRequestDTO request) {

        PaymentCreateResponseDTO response =
                paymentService.createPaymentOrder(request);

        return ResponseEntity.ok(response);
    }

    // Verify Payment
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequestDTO request) {

        String result = paymentService.verifyPayment(request);

        return ResponseEntity.ok(result);
    }

}
