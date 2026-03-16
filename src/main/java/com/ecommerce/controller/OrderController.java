package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.payloads.CreateOrderRequest;
import com.ecommerce.payloads.OrderDTO;
import com.ecommerce.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder( @Valid @RequestBody CreateOrderRequest request) {

        OrderDTO order = orderService.createOrder(request);

        return ResponseEntity.ok(order);
    }

    // Get order by id
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderId") Long orderId) {

        OrderDTO order = orderService.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }
}