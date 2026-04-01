package com.ecommerce.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;
import com.ecommerce.entities.Order;
import com.ecommerce.payloads.PaymentCreateRequestDTO;
import com.ecommerce.payloads.PaymentCreateResponseDTO;
import com.ecommerce.payloads.PaymentVerifyRequestDTO;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.OrderRepository;
import com.ecommerce.services.PaymentService;
import com.razorpay.RazorpayClient;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
	
	private  OrderRepository orderRepository;
    private  CartRepository cartRepository;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;
    
    public PaymentServiceImpl(OrderRepository orderRepository,CartRepository cartRepository) {
           this.orderRepository = orderRepository;
           this.cartRepository = cartRepository;
    }

	
    
    @Override
    public PaymentCreateResponseDTO createPaymentOrder(PaymentCreateRequestDTO request) {

        // 1️⃣ Fetch Cart
        Cart cart = cartRepository.findByUserId(request.getUserId());

        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2️⃣ Calculate total amount
        double totalAmount = 0;
        for (CartItem item : cart.getCartItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

        try {
            // 3️⃣ Initialize Razorpay
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);

            // 4️⃣ Create Razorpay Order
            JSONObject options = new JSONObject();
            options.put("amount", (long)(totalAmount * 100)); // ₹ → paise
            options.put("currency", "INR");
            options.put("receipt", "order_rcptid_" + System.currentTimeMillis());

            com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);

            String razorpayOrderId = razorpayOrder.get("id");

            // 5️⃣ Save Order in DB
            Order order = new Order();
            order.setOrderDate(java.time.LocalDateTime.now());
            order.setTotalAmount(totalAmount);
            order.setStatus("PENDING");
            order.setPaymentStatus("PENDING");
            order.setRazorpayOrderId(razorpayOrderId);
            order.setUser(cart.getUser());
            

            orderRepository.save(order);

            // 6️⃣ Return response
            return new PaymentCreateResponseDTO(
                    razorpayOrderId,
                    totalAmount,
                    "INR"
            );

        } catch (Exception e) {
            throw new RuntimeException("Error creating payment order: " + e.getMessage());
        }
    }

    @Override
    public String verifyPayment(PaymentVerifyRequestDTO request) {

        try {
            String orderId = request.getRazorpayOrderId();
            String paymentId = request.getRazorpayPaymentId();
            String signature = request.getRazorpaySignature();

            // Find Order
            Order order = orderRepository.findByRazorpayOrderId(orderId);

            if (order == null) {
                throw new RuntimeException("Order not found");
            }

            // Create data string
            String data = orderId + "|" + paymentId;

            // Generate HMAC SHA256 signature
            String generatedSignature = hmacSHA256(data, razorpaySecret);

            // Compare signatures
            if (generatedSignature.equals(signature)) {

                // ✅ SUCCESS
                order.setPaymentStatus("SUCCESS");
                order.setPaymentId(paymentId);
                order.setStatus("CONFIRMED");

                orderRepository.update(order);

                return "Payment successful";

            } else {

                // ❌ FAILED
                order.setPaymentStatus("FAILED");
                order.setStatus("FAILED");

                orderRepository.update(order);

                return "Payment verification failed";
            }

        } catch (Exception e) {
            return "Payment failed: " + e.getMessage();
        }
    }
    
    private static  String hmacSHA256(String data, String secret) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secretKey =
                    new javax.crypto.spec.SecretKeySpec(secret.getBytes(), "HmacSHA256");

            mac.init(secretKey);

            byte[] rawHmac = mac.doFinal(data.getBytes());

            // Convert to hex
            StringBuilder hex = new StringBuilder(2 * rawHmac.length);
            for (byte b : rawHmac) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC: " + e.getMessage());
        }
    }
    
    
    public static void main(String[] args) {
    	
        String data = "order_SXwND715XroacL|pay_test_123";
        String secret = "your_secret";

        String signature = hmacSHA256(data, "");
        System.out.println(signature);
    }

}
