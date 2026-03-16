package com.ecommerce.services;

import com.ecommerce.payloads.AddToCartRequest;
import com.ecommerce.payloads.CartDTO;

public interface CartService {
	
	CartDTO addProductToCart(AddToCartRequest request);
	
	CartDTO getCartByUserId(Long userId);

    void removeItemFromCart(Long cartItemId);

}
