package com.retailstore.products.mapper;

import com.retailstore.products.dto.ProductRequestDTO;
import com.retailstore.products.dto.ProductResponseDTO;
import com.retailstore.products.entity.Products;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    ProductResponseDTO productToProductResponseDTO(Products product);
    
    @Mapping(target = "id", ignore = true)
    Products productRequestDTOToProduct(ProductRequestDTO productRequestDTO);
}