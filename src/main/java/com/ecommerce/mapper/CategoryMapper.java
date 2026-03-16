package com.ecommerce.mapper;

import com.ecommerce.entities.Category;
import com.ecommerce.payloads.CategoryDTO;

public class CategoryMapper {
	
	// Entity → DTO
    public static CategoryDTO mapToDTO(Category category) {

        if (category == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }

    // DTO → Entity
    public static Category mapToEntity(CategoryDTO dto) {

        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return category;
    }

}
