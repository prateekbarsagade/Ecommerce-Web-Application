package com.ecommerce.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Repository
public class UserRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager
                .createQuery("FROM User", User.class)
                .getResultList();
    }

    public User findByEmail(String email) {
        return entityManager
                .createQuery("FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
    
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

}
