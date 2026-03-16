package com.ecommerce.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payloads.OrderDTO;
import com.ecommerce.services.OrderService;


@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
	
	private final OrderService orderService;
	
	public AdminOrderController(OrderService orderService) {
		this.orderService =orderService;
	}
	
// Get All Orders
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {

        List<OrderDTO> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }
    
 // Get Order By ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id) {

        OrderDTO order = orderService.getOrderById(id);

        return ResponseEntity.ok(order);
    }
    
 // Update Order Status
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable("id") Long id,@RequestParam("status") String status) {

        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);

        return ResponseEntity.ok(updatedOrder);
    }
    
 // Cancel/Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable("id") Long id) {

        orderService.cancelOrder(id);

        return ResponseEntity.noContent().build();
    }
}
