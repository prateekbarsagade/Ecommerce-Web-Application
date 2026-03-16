package com.ecommerce.services;

import java.util.List;

import com.ecommerce.payloads.CreateOrderRequest;
import com.ecommerce.payloads.OrderDTO;

public interface OrderService {
	
	OrderDTO createOrder(CreateOrderRequest request);

    OrderDTO getOrderById(Long orderId);
    
 // ADMIN METHODS
    List<OrderDTO> getAllOrders();

    OrderDTO updateOrderStatus(Long orderId, String status);

    void cancelOrder(Long orderId);

}
