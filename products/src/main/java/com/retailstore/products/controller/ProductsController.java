package com.retailstore.products.controller;

import com.retailstore.products.dto.ProductRequestDTO;
import com.retailstore.products.dto.ProductResponseDTO;
import com.retailstore.products.exception.ProductNotFoundException;
import com.retailstore.products.exception.ResourceIsAlreadyThereException;
import com.retailstore.products.service.ProductsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/products/v1")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        try {
            ProductResponseDTO productResponseDTO = productsService.addProduct(productRequestDTO);
            log.info("Product created successfully with name: {}", productRequestDTO.getName());
            return new ResponseEntity<>(productResponseDTO, HttpStatusCode.valueOf(201));
        } catch (ResourceIsAlreadyThereException e) {
            log.error("Product already exists with name: {}", productRequestDTO.getName());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/searchProduct/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        try {
            ProductResponseDTO productResponseDTO = productsService.getProductById(productId);
            log.info("Product found with id: {}", productId);
            return new ResponseEntity<>(productResponseDTO, HttpStatusCode.valueOf(200));
        } catch (ProductNotFoundException e) {
            log.error("Product not found with id: {}", productId);
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            log.error("Error finding product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<?> getProductByProductNamePriceCategoryAndBrand(@RequestParam Map<String, String> map) {
        
        String name = map.get("name");
        String price = map.get("price");
        String category = map.get("category");
        String brand = map.get("brand");
        try {
            List<ProductResponseDTO> productResponseDTOs = productsService.getProductByProductNameAndPrice(name, price, category, brand);
            log.info("Retrieved all products, count: {}", productResponseDTOs.size());
            return new ResponseEntity<>(productResponseDTOs, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            log.error("Error retrieving products: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductResponseDTO> productResponseDTOs = productsService.getAllProducts();
            log.info("Retrieved all products, count: {}", productResponseDTOs.size());
            return new ResponseEntity<>(productResponseDTOs, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            log.error("Error retrieving products: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        try {
            ProductResponseDTO productResponseDTO = productsService.updateProduct(productId, productRequestDTO);
            log.info("Product updated successfully with id: {}", productId);
            return new ResponseEntity<>(productResponseDTO, HttpStatusCode.valueOf(200));
        } catch (ProductNotFoundException e) {
            log.error("Product not found with id: {}", productId);
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            log.error("Error updating product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            boolean result = productsService.deleteProduct(productId);
            log.info("Product deleted successfully with id: {}", productId);
            return new ResponseEntity<>("Product Deleted Successfully", HttpStatusCode.valueOf(204));
        } catch (ProductNotFoundException e) {
            log.error("Product not found with id: {}", productId);
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }
}
