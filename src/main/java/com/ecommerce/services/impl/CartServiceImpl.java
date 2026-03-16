package com.ecommerce.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;
import com.ecommerce.mapper.CartMapper;
import com.ecommerce.payloads.AddToCartRequest;
import com.ecommerce.payloads.CartDTO;
import com.ecommerce.repositories.CartItemRepository;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.services.CartService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
	public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
			ProductRepository productRepository, UserRepository userRepository) {

		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Override
	public CartDTO addProductToCart(AddToCartRequest request) {
		
		User user = userRepository.findById(request.getUserId());
		
		Cart cart;

        try {
            cart = cartRepository.findByUserId(user.getId());
        } catch (Exception e) {

            cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());

            cartRepository.save(cart);
        }
        
        Product product = productRepository.findById(request.getProductId());
        
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem != null) {

            // product already exists in cart
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());

            cartItemRepository.update(existingItem);

        } else {

            // new cart item
            CartItem cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());

            cartItemRepository.save(cartItem);
        }   

        return CartMapper.mapToCartDTO(cart);
	}

	@Override
	public CartDTO getCartByUserId(Long userId) {
		
		Cart cart = cartRepository.findByUserId(userId);

        return CartMapper.mapToCartDTO(cart);
	}

	@Override
	public void removeItemFromCart(Long cartItemId) {
		
		CartItem item = cartItemRepository.findById(cartItemId);

        cartItemRepository.delete(item);
		
	}

}
