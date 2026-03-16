package com.ecommerce.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payloads.CategoryDTO;
import com.ecommerce.services.CategoryService;



@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	
	private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
 // Create Category
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {

        CategoryDTO createdCategory = categoryService.createCategory(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCategory);
    }
    
 // Update Category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory( @PathVariable("id") Long id, @RequestBody CategoryDTO dto) {

        CategoryDTO updatedCategory = categoryService.updateCategory(id, dto);

        return ResponseEntity.ok(updatedCategory);
    }
    
 // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
    
 // Get All Categories
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        List<CategoryDTO> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }
    
    // Get Category By ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {

        CategoryDTO category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(category);
    }

	
	
}
