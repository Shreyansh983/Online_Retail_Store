package com.retailstore.inventory.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String brand;
    private String category;
}
