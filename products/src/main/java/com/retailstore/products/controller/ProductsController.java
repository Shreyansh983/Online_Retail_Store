package com.retailstore.products.controller;

import com.retailstore.products.entity.Products;
import com.retailstore.products.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api2")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @PostMapping("/addProducts")
    public Products addProducts(@RequestBody Products products){
        return productsService.addProducts(products);
    }

    @GetMapping("/searchProducts/{productId}")
    public Products getProductsById(@PathVariable Long productId){
        return productsService.getProductsById(productId);
    }

    @PutMapping("/updateProducts/{productId}")
    public Products updateProducts(@PathVariable Long productId,@RequestBody Products products){
        return productsService.updateProducts(productId,products);
    }

    @DeleteMapping("/deleteProducts/{productId}")
    public String deleteProducts(@PathVariable Long productId){
        boolean result = productsService.deleteProducts(productId);
        if(result)
            return "Deleted Successfully";
        else
            return "Not Deleted Successfully";
    }
}
