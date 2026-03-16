package com.ecommerce.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.entities.OrderItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class OrderItemRepository {
	
	
	@PersistenceContext
    private EntityManager entityManager;

    public OrderItem save(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem;
    }

    public OrderItem findById(Long id) {
        return entityManager.find(OrderItem.class, id);
    }

    public List<OrderItem> findByOrderId(Long orderId) {

        String jpql = "SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId";

        return entityManager.createQuery(jpql, OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
