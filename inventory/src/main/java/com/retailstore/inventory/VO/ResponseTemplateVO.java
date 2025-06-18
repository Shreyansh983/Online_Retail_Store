package com.retailstore.inventory.VO;

import com.retailstore.inventory.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVO {

    private Inventory inventory;
    private Products products;
}
