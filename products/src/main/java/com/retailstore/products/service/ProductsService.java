package com.retailstore.products.service;

import com.retailstore.products.entity.Products;
import com.retailstore.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    public Products addProducts(Products products){
        return productsRepository.save(products);
    }

    public Products getProductsById(Long productId){
        return productsRepository.findByProductId(productId);
    }

    public Products updateProducts(Long productId,Products products){
        Products products1 = getProductsById(productId);
        if(products1!=null){
            products1.setProductName(products.getProductName());
            products1.setProductDescription(products.getProductDescription());
            products1.setProductPrice(products.getProductPrice());
            return productsRepository.save(products1);
        }
        return null;
    }

    public boolean deleteProducts(Long productId){
        Products products = getProductsById(productId);
        if (products!=null) {
            productsRepository.delete(products);
            return true;
        }
        else
            return false;
    }
}
