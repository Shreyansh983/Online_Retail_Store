package com.retailstore.inventory.controller;

import com.retailstore.inventory.VO.ResponseTemplateVO;
import com.retailstore.inventory.entity.Inventory;
import com.retailstore.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api3")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @PostMapping("/addInventory")
    public Inventory addInventory(@RequestBody Inventory inventory){
        return inventoryService.addInventory(inventory);
    }

    @GetMapping("/searchInventory/{inventoryId}")
    public ResponseTemplateVO getInventoryById(@PathVariable Long inventoryId){
        return inventoryService.getInventoryById(inventoryId);
    }

    @PutMapping("/updateInventory/{inventoryId}")
    public Inventory updateInventory(@PathVariable Long inventoryId,@RequestBody Inventory inventory){
        return inventoryService.updateInventory(inventoryId,inventory);
    }

    @DeleteMapping("/deleteInventory/{inventoryId}")
    public String deleteInventory(@PathVariable Long inventoryId){
        boolean result = inventoryService.deleteInventory(inventoryId);
        if(result==true)
            return "Deleted Successfully";
        else
            return "Not Deleted Successfully";
    }
}
