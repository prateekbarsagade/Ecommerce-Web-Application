package com.ecommerce.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.OrderItem;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.payloads.CreateOrderRequest;
import com.ecommerce.payloads.OrderDTO;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.OrderItemRepository;
import com.ecommerce.repositories.OrderRepository;
import com.ecommerce.services.OrderService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CartRepository cartRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {

        Cart cart = cartRepository.findByUserId(request.getUserId());

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setShippingAddress(request.getShippingAddress());

        List<OrderItem> orderItems = new ArrayList<>();

        double totalAmount = 0;

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());

            totalAmount += cartItem.getPrice() * cartItem.getQuantity();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        return OrderMapper.mapToOrderDTO(order);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId);

        return OrderMapper.mapToOrderDTO(order);
    }

	@Override
	public List<OrderDTO> getAllOrders() {
		
		    List<Order> orders = orderRepository.findAll();

		    List<OrderDTO> orderDTOs = new ArrayList<>();

		    for (Order order : orders) {
		        orderDTOs.add(OrderMapper.mapToOrderDTO(order));
		    }

		    return orderDTOs;
	}

	@Override
	public OrderDTO updateOrderStatus(Long orderId, String status) {
		Order order = orderRepository.findById(orderId);

	    order.setStatus(status);

	    orderRepository.save(order);

	    return OrderMapper.mapToOrderDTO(order);
	}

	@Override
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId);

	    order.setStatus("CANCELLED");

	    orderRepository.save(order);
	}

}
