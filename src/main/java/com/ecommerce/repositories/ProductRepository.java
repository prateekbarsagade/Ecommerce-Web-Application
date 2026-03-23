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
	
	public List<Product> findAllWithPagination(int page, int size) {

	    int offset = page * size;

	    String jpql = "SELECT p FROM Product p";

	    return entityManager.createQuery(jpql, Product.class)
	            .setFirstResult(offset)   // OFFSET
	            .setMaxResults(size)      // LIMIT
	            .getResultList();
	}
	
	public long countAllProducts() {

	    String jpql = "SELECT COUNT(p) FROM Product p";

	    return entityManager.createQuery(jpql, Long.class)
	            .getSingleResult();
	}
	
	public List<Product> searchProducts(String keyword, int page, int size) {

	    int offset = page * size;

	    String jpql = "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:keyword)";

	    return entityManager.createQuery(jpql, Product.class)
	            .setParameter("keyword", "%" + keyword + "%") 
	            .setFirstResult(offset)  // OFFSET
	            .setMaxResults(size)     // LIMIT
	            .getResultList();
	}
	
	public long countSearchProducts(String keyword) {

	    String jpql = "SELECT COUNT(p) FROM Product p WHERE LOWER(p.name) LIKE LOWER(:keyword)";

	    return entityManager.createQuery(jpql, Long.class)
	            .setParameter("keyword", "%" + keyword + "%")
	            .getSingleResult();
	}
	
	

}
