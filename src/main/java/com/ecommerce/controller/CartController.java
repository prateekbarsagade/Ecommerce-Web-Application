package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.payloads.AddToCartRequest;
import com.ecommerce.payloads.CartDTO;
import com.ecommerce.services.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to cart
    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(
            @Valid @RequestBody AddToCartRequest request) {

        CartDTO cart = cartService.addProductToCart(request);

        return ResponseEntity.ok(cart);
    }

    // Get cart by user
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable("userId") Long userId) {

        CartDTO cart = cartService.getCartByUserId(userId);

        return ResponseEntity.ok(cart);
    }

    // Remove item from cart
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<String> removeItem(@PathVariable("cartItemId") Long cartItemId) {

        cartService.removeItemFromCart(cartItemId);

        return ResponseEntity.ok("Item removed from cart");
    }
}