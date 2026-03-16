package com.ecommerce.repositories;

import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Repository
public class CartRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

    public Cart save(Cart cart) {
        entityManager.persist(cart);
        return cart;
    }

    public Cart update(Cart cart) {
        return entityManager.merge(cart);
    }

    public Cart findById(Long id) {
        return entityManager.find(Cart.class, id);
    }

    public Cart findByUserId(Long userId) {
        String jpql = "SELECT c FROM Cart c WHERE c.user.id = :userId";
        return entityManager.createQuery(jpql, Cart.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public void delete(Cart cart) {
        entityManager.remove(entityManager.contains(cart) ? cart : entityManager.merge(cart));
    }

}
