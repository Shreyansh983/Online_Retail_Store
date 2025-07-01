package com.retailstore.products.service;

import com.retailstore.products.dto.ProductRequestDTO;
import com.retailstore.products.dto.ProductResponseDTO;
import com.retailstore.products.entity.Products;
import com.retailstore.products.exception.ProductNotFoundException;
import com.retailstore.products.exception.ResourceIsAlreadyThereException;
import com.retailstore.products.mapper.ProductMapper;
import com.retailstore.products.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) throws ResourceIsAlreadyThereException {
        String name = productRequestDTO.getName();
        log.info("Attempting to create product with name: {}", name);
        Products existingProduct = productsRepository.findByName(name);
        if (existingProduct != null) {
            log.error("Product with name: {} already exists", name);
            throw new ResourceIsAlreadyThereException("Product with name: " + name + " already exists");
        }
        Products product = productMapper.productRequestDTOToProduct(productRequestDTO);
        Products savedProduct = productsRepository.save(product);
        log.info("Successfully created product with id: {}", savedProduct.getId());
        return productMapper.productToProductResponseDTO(savedProduct);
    }

    public ProductResponseDTO getProductById(Long productId) throws ProductNotFoundException {
        log.info("Searching for product with id: {}", productId);
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", productId);
                    return new ProductNotFoundException("Product not found with id: " + productId);
                });
        log.info("Found product with id: {}", productId);
        return productMapper.productToProductResponseDTO(product);
    }

    public List<ProductResponseDTO> getProductByProductNameAndPrice(String name, String price, String category, String brand) {
        log.info("Searching for products with parameters - name: {}, price: {}, category: {}, brand: {}", name, price, category, brand);
        List<Products> products = productsRepository.findAll();
        List<Products> filteredProducts = products.stream()
                .filter(product -> (name == null || product.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(product -> (price == null || product.getPrice().toString().equals(price)))
                .filter(product -> (category == null || product.getCategory() != null && product.getCategory().toLowerCase().contains(category.toLowerCase())))
                .filter(product -> (brand == null || product.getBrand() != null && product.getBrand().toLowerCase().contains(brand.toLowerCase())))
                .toList();

        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        filteredProducts.forEach(product -> {
            productResponseDTOs.add(productMapper.productToProductResponseDTO(product));
        });
        log.info("Found {} products matching the search criteria", productResponseDTOs.size());
        return productResponseDTOs;
    }

    public List<ProductResponseDTO> getAllProducts() {
        log.info("Retrieving all products");
        List<Products> products = productsRepository.findAll();
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
        products.forEach(product -> {
            productResponseDTOs.add(productMapper.productToProductResponseDTO(product));
        });
        log.info("Found {} products", products.size());
        return productResponseDTOs;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) throws ProductNotFoundException {
        log.info("Attempting to update product with id: {}", productId);
        Products existingProduct = productsRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", productId);
                    return new ProductNotFoundException("Product not found with id: " + productId);
                });

        Products updatedProduct = productMapper.productRequestDTOToProduct(productRequestDTO);
        updatedProduct.setId(existingProduct.getId());

        Products savedProduct = productsRepository.save(updatedProduct);
        log.info("Successfully updated product with id: {}", productId);
        return productMapper.productToProductResponseDTO(savedProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProduct(Long productId) throws ProductNotFoundException {
        log.info("Attempting to delete product with id: {}", productId);
        Optional<Products> product = productsRepository.findById(productId);
        if (product.isEmpty()) {
            log.error("Product not found with id: {}", productId);
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
        productsRepository.deleteById(productId);
        log.info("Successfully deleted product with id: {}", productId);
        return true;
    }
}
