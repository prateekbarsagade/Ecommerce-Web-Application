package com.ecommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;
import com.ecommerce.payloads.CartDTO;
import com.ecommerce.payloads.CartItemDTO;

public class CartMapper {
	
	
	public static CartDTO mapToCartDTO(Cart cart) {

        CartDTO cartDTO = new CartDTO();

        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());

        List<CartItemDTO> items = cart.getCartItems()
                .stream()
                .map(CartMapper::mapToCartItemDTO)
                .collect(Collectors.toList());

        cartDTO.setItems(items);

        double totalAmount = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        cartDTO.setTotalAmount(totalAmount);

        return cartDTO;
    }

    public static CartItemDTO mapToCartItemDTO(CartItem cartItem) {

        CartItemDTO dto = new CartItemDTO();

        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getPrice());

        return dto;
    }

}
