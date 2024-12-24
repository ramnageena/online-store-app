package com.online_store.service.impl;

import com.online_store.entity.Product;
import com.online_store.exception.ProductNotFoundException;
import com.online_store.exception.ProductServiceException;
import com.online_store.reposiitory.ProductRepository;
import com.online_store.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product createProduct(Product product) {
        try {
            logger.info("Attempting to create a new product.");
            Product savedProduct = productRepository.save(product);
            logger.info("Product successfully created with ID: {}", savedProduct.getId());
            return savedProduct;
        } catch (Exception e) {
            logger.error("Error occurred while creating a product: {}", e.getMessage());
            throw new ProductServiceException("Failed to create the product", e);
        }
    }

    @Override
    public List<Product> getAllProduct() {
        try {
            logger.info("Fetching all products.");
            List<Product> productList = productRepository.findAll();
            logger.info("Retrieved all products. Total count: {}", productList.size());
            return productList;
        } catch (Exception e) {
            logger.error("Error occurred while fetching all products: {}", e.getMessage());
            throw new ProductServiceException("Failed to fetch all products", e);
        }
    }

    @Override
    public Product getProductById(Long productId) {
        try {
            logger.info("Fetching product with ID: {}", productId);
            return productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID : " + productId));
        } catch (ProductNotFoundException e) {
            logger.warn("Product not found with ID: {}. Error: {}", productId, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while fetching the product with ID: {}. Error: {}", productId, e.getMessage());
            throw new ProductServiceException("Failed to fetch the product", e);
        }
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        try {
            logger.info("Attempting to update the product with ID: {}", productId);
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setStock(product.getStock());

            Product updatedProduct = productRepository.save(existingProduct);
            logger.info("Product successfully updated with ID: {}", updatedProduct.getId());
            return updatedProduct;
        } catch (ProductNotFoundException e) {
            logger.warn("Product not found with ID: {}.Error: {}", productId, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while updating the product with ID: {}. Error: {}", productId, e.getMessage());
            throw new ProductServiceException("Failed to update the product", e);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        try {
            logger.info("Attempting to delete the product with ID: {}", productId);
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

            productRepository.delete(existingProduct);
            logger.info("Product successfully deleted with ID: {}", productId);
        } catch (ProductNotFoundException e) {
            logger.warn("Product not found with ID: {}.  Error: {}", productId, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while deleting the product with ID: {}. Error: {}", productId, e.getMessage());
            throw new ProductServiceException("Failed to delete the product", e);
        }
    }
}
