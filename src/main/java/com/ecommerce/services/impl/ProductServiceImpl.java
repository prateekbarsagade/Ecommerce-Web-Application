package com.ecommerce.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.entities.Category;
import com.ecommerce.entities.Product;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.payloads.PageResponse;
import com.ecommerce.payloads.ProductDTO;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.services.ProductService;

import jakarta.transaction.Transactional;



@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        Category category = categoryRepository.findById(productDTO.getCategoryId());

        if (category == null) {
            throw new ResourceNotFoundException(
                    "Category not found with id " + productDTO.getCategoryId());
        }

        Product product = ProductMapper.mapToEntity(productDTO, category);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.mapToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }

        Category category = categoryRepository.findById(productDTO.getCategoryId());

        if (category == null) {
            throw new ResourceNotFoundException(
                    "Category not found with id " + productDTO.getCategoryId());
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(category);
        product.setImageUrl(productDTO.getImageUrl());

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.mapToDTO(updatedProduct);
    }

    @Override
    public ProductDTO getProductById(Long productId) {

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }

        return ProductMapper.mapToDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }

        productRepository.delete(productId);
    }

    @Override
    public PageResponse<ProductDTO> getAllProducts(String keyword, int page, int size) {

        // Basic validation (optional)
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        List<Product> products;
        long totalElements;

        // Decide: search or normal
        if (keyword != null && !keyword.trim().isEmpty()) {

            // Search + Pagination
            products = productRepository.searchProducts(keyword, page, size);
            totalElements = productRepository.countSearchProducts(keyword);

        } else {

            //  Normal Pagination
            products = productRepository.findAllWithPagination(page, size);
            totalElements = productRepository.countAllProducts();
        }

        //  Convert Entity → DTO
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductMapper::mapToDTO)
                .toList();

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalElements / size);

        // Build response
        PageResponse<ProductDTO> response = new PageResponse<>();
        response.setContent(productDTOs);
        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);

        return response;
    }

}
