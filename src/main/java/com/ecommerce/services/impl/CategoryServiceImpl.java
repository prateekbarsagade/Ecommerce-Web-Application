package com.ecommerce.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.entities.Category;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.payloads.CategoryDTO;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.services.CategoryService;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // CREATE CATEGORY
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = CategoryMapper.mapToEntity(categoryDTO);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.mapToDTO(savedCategory);
    }

    // UPDATE CATEGORY
    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {

        Category category = categoryRepository.findById(categoryId);

        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.mapToDTO(updatedCategory);
    }

    // GET SINGLE CATEGORY
    @Override
    public CategoryDTO getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId);

        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        return CategoryMapper.mapToDTO(category);
    }

    // GET ALL CATEGORIES
    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    // DELETE CATEGORY
    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId);

        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        categoryRepository.delete(categoryId);
    }

}
