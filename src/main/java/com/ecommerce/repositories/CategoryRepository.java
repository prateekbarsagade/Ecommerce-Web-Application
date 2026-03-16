package com.ecommerce.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Category;
import com.ecommerce.entities.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Repository
public class CategoryRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

	
    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }
    
    

    public List<Category> findAll() {
        return entityManager
                .createQuery("FROM Category", Category.class)
                .getResultList();
    }
    
    
    
    public Category findById(Long CategoryId) {
    	return entityManager.find(Category.class, CategoryId);
    }
    
    
    
    public void delete(Long CategoryId) {
    	Category category = entityManager.find(Category.class, CategoryId);
        if (category != null) {
            entityManager.remove(category);
        }
    }

}
