package com.retailstore.inventory.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    private Long productId;
    private String productName;
    private String productDescription;
    private Double productPrice;

}
