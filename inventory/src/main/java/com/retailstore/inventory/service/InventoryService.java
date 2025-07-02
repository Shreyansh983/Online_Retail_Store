package com.retailstore.inventory.service;

import com.retailstore.inventory.VO.Products;
import com.retailstore.inventory.VO.ResponseTemplateVO;
import com.retailstore.inventory.dto.InventoryRequestDTO;
import com.retailstore.inventory.dto.InventoryResponseDTO;
import com.retailstore.inventory.entity.Inventory;
import com.retailstore.inventory.exception.ResourceIsAlreadyThereException;
import com.retailstore.inventory.external.service.ProductsService;
import com.retailstore.inventory.mapper.InventoryMapper;
import com.retailstore.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductsService productsService;

    private final InventoryMapper inventoryMapper = InventoryMapper.INSTANCE;

    @Transactional(rollbackFor = Exception.class)
    public InventoryResponseDTO addInventory(InventoryRequestDTO inventoryRequestDTO) {
        Long productId = inventoryRequestDTO.getProductId();
        Inventory inventory = inventoryRepository.findById(productId).orElse(Inventory.builder().productId(productId)
                .reservedQuantity(0)
                .availableQuantity(0)
                .build());
        Products products = productsService.getProducts(productId);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + inventoryRequestDTO.getQuantityToAdd());
        Inventory savedInventory = inventoryRepository.save(inventory);
        String stockStatus = inventory.getAvailableQuantity() > 0 ? "IN_STOCK" : "OUT_OF_STOCK";
        InventoryResponseDTO inventoryResponseDTO = inventoryMapper.inventoryToInventoryResponseDTO(savedInventory);
        inventoryResponseDTO.setProducts(products);
        inventoryResponseDTO.setStockStatus(stockStatus);
        return inventoryResponseDTO;
    }

    public InventoryResponseDTO getInventoryByproductId(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId).orElseThrow(() -> new RuntimeException("not found"));
        Products products = productsService.getProducts(inventory.getProductId());
        String stockStatus = inventory.getAvailableQuantity() > 0 ? "IN_STOCK" : "OUT_OF_STOCK";
        InventoryResponseDTO inventoryResponseDTO = inventoryMapper.inventoryToInventoryResponseDTO(inventory);
        inventoryResponseDTO.setProducts(products);
        inventoryResponseDTO.setStockStatus(stockStatus);
        return inventoryResponseDTO;
    }

//    public Inventory updateInventory(Long inventoryId,Inventory inventory){
//        Inventory inventory1 = inventoryRepository.findByInventoryId(inventoryId);
//        if(inventory1!=null){
////            inventory1.setQuantity(inventory.getQuantity());
//            return inventoryRepository.save(inventory1);
//        }
//        return null;
//    }
//
//    public boolean deleteInventory(Long inventoryId){
//        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
//        if(inventory!=null){
//            inventoryRepository.delete(inventory);
//            return true;
//        }
//        else
//            return false;
//    }
}
