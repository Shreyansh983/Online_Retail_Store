package com.retailstore.inventory.service;

import com.retailstore.inventory.VO.Products;
import com.retailstore.inventory.VO.ResponseTemplateVO;
import com.retailstore.inventory.entity.Inventory;
import com.retailstore.inventory.external.service.ProductsService;
import com.retailstore.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private ProductsService productsService;

    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public ResponseTemplateVO getInventoryById(Long inventoryId) {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
//        Products products =
//                restTemplate.getForObject("http://PRODUCTS-SERVICE/api2/searchProducts/"
//                        +inventory.getProductId(),Products.class);
        Products products = productsService.getProducts(inventory.getProductId());
        responseTemplateVO.setInventory(inventory);
        responseTemplateVO.setProducts(products);
        return responseTemplateVO;
    }

    public Inventory updateInventory(Long inventoryId,Inventory inventory){
        Inventory inventory1 = inventoryRepository.findByInventoryId(inventoryId);
        if(inventory1!=null){
            inventory1.setQuantity(inventory.getQuantity());
            return inventoryRepository.save(inventory1);
        }
        return null;
    }

    public boolean deleteInventory(Long inventoryId){
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
        if(inventory!=null){
            inventoryRepository.delete(inventory);
            return true;
        }
        else
            return false;
    }
}
