package com.retailstore.inventory.controller;

import com.retailstore.inventory.dto.InventoryRequestDTO;
import com.retailstore.inventory.dto.InventoryResponseDTO;
import com.retailstore.inventory.exception.ResourceIsAlreadyThereException;
import com.retailstore.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/inventory/v1")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @PostMapping("/addInventory")
    public ResponseEntity<?> addInventory(@RequestBody InventoryRequestDTO inventoryRequestDTO){
        try {
            InventoryResponseDTO inventoryResponseDTO = inventoryService.addInventory(inventoryRequestDTO);
            return new ResponseEntity<>(inventoryResponseDTO, HttpStatusCode.valueOf(201)) ;
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/searchInventory/{productId}")
    public ResponseEntity<?> getInventoryById(@PathVariable Long productId){
        try {
            InventoryResponseDTO inventoryResponseDTO = inventoryService.getInventoryByproductId(productId);
            return new ResponseEntity<>(inventoryResponseDTO, HttpStatusCode.valueOf(201)) ;
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

//
//    @PutMapping("/updateInventory/{inventoryId}")
//    public Inventory updateInventory(@PathVariable Long inventoryId,@RequestBody Inventory inventory){
//        return inventoryService.updateInventory(inventoryId,inventory);
//    }
//
//    @DeleteMapping("/deleteInventory/{inventoryId}")
//    public String deleteInventory(@PathVariable Long inventoryId){
//        boolean result = inventoryService.deleteInventory(inventoryId);
//        if(result==true)
//            return "Deleted Successfully";
//        else
//            return "Not Deleted Successfully";
//    }
}
