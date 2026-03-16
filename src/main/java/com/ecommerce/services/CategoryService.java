package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.Category;
import com.ecommerce.payloads.CategoryDTO;

public interface CategoryService {
	
	CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Long categoryId);

    List<CategoryDTO> getAllCategories();

    void deleteCategory(Long categoryId);
}
