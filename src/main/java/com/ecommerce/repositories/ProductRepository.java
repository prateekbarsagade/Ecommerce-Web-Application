package com.ecommerce.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Repository
public class ProductRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public Product save(Product product) {
		entityManager.persist(product);
        return product;
	}
	
	public Product findById(Long id) {
		return entityManager.find(Product.class, id);
	}
	
	public List<Product> findAll(){
		return entityManager
                .createQuery("FROM Product", Product.class)
                .getResultList();
	}
	
	public void delete(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

}
