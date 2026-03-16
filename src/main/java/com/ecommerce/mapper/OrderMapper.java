package com.ecommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.entities.Order;
import com.ecommerce.entities.OrderItem;
import com.ecommerce.payloads.OrderDTO;
import com.ecommerce.payloads.OrderItemDTO;

public class OrderMapper {
	
	public static OrderDTO mapToOrderDTO(Order order) {

        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderItemDTO> items = order.getOrderItems()
                .stream()
                .map(OrderMapper::mapToOrderItemDTO)
                .collect(Collectors.toList());

        dto.setItems(items);

        return dto;
    }

    public static OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {

        OrderItemDTO dto = new OrderItemDTO();

        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());

        return dto;
    }

}
