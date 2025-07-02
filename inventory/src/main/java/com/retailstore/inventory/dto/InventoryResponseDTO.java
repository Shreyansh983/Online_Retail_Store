package com.retailstore.inventory.dto;

import com.retailstore.inventory.VO.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDTO {
    private Long productId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private String stockStatus;
    private Products products;
}

