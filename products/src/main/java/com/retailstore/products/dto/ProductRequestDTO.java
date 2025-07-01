package com.retailstore.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    
    private String description;
    
    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Product price must be positive")
    private Double price;
    
    private String brand;
    
    private String category;
}