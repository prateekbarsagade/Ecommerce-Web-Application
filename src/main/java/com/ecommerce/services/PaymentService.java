package com.ecommerce.services;

import com.ecommerce.payloads.PaymentCreateRequestDTO;
import com.ecommerce.payloads.PaymentCreateResponseDTO;
import com.ecommerce.payloads.PaymentVerifyRequestDTO;

public interface PaymentService {
	
	//Create Razorpay Order
    PaymentCreateResponseDTO createPaymentOrder(PaymentCreateRequestDTO request);

    //  Verify Payment
    String verifyPayment(PaymentVerifyRequestDTO request);

}
