package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.Product;
import com.ecommerce.payloads.ProductDTO;

public interface ProductService {
	
	ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO getProductById(Long productId);

    List<ProductDTO> getAllProducts();

    void deleteProduct(Long productId);
}
