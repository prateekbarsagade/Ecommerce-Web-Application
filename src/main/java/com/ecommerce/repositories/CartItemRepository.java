package com.ecommerce.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CartItemRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

    public CartItem save(CartItem cartItem) {
        entityManager.persist(cartItem);
        return cartItem;
    }

    public CartItem update(CartItem cartItem) {
        return entityManager.merge(cartItem);
    }

    public CartItem findById(Long id) {
        return entityManager.find(CartItem.class, id);
    }

    public List<CartItem> findByCartId(Long cartId) {
        String jpql = "SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId";

        return entityManager.createQuery(jpql, CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    public void delete(CartItem cartItem) {
        entityManager.remove(entityManager.contains(cartItem) ? cartItem : entityManager.merge(cartItem));
    }
    
    public CartItem findByCartIdAndProductId(Long cartId, Long productId) {

        String jpql = "SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId";

        List<CartItem> items = entityManager.createQuery(jpql, CartItem.class)
                .setParameter("cartId", cartId)
                .setParameter("productId", productId)
                .getResultList();

        return items.isEmpty() ? null : items.get(0);
    }

}
