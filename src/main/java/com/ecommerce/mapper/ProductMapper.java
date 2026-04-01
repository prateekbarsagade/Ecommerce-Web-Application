package com.ecommerce.mapper;

import com.ecommerce.entities.Category;
import com.ecommerce.entities.Product;
import com.ecommerce.payloads.ProductDTO;

public class ProductMapper {
	
	// Entity → DTO
    public static ProductDTO mapToDTO(Product product) {

        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }

        return dto;
    }

    // DTO → Entity
    public static Product mapToEntity(ProductDTO dto, Category category) {

        if (dto == null) {
            return null;
        }

        Product product = new Product();

        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category);
        product.setImageUrl(dto.getImageUrl());

        return product;
    }

}
