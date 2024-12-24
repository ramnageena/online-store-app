package com.online_store.service;

import com.online_store.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    List<Product> getAllProduct();
    Product getProductById(Long productId);
    Product updateProduct(Product product,Long productId);
    void deleteProduct(Long productId);
}
