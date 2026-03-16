package com.ecommerce.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class OrderRepository {
	
	
	@PersistenceContext
    private EntityManager entityManager;

    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    public Order update(Order order) {
        return entityManager.merge(order);
    }

    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> findByUserId(Long userId) {

        String jpql = "SELECT o FROM Order o WHERE o.user.id = :userId";

        return entityManager.createQuery(jpql, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public List<Order> findAll() {

        String jpql = "SELECT o FROM Order o ORDER BY o.orderDate DESC";

        return entityManager.createQuery(jpql, Order.class)
                .getResultList();
    }
}
