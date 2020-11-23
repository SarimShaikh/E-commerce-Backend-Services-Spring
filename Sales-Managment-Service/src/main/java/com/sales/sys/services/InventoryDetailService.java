package com.sales.sys.services;

import com.sales.sys.entities.InventoryDetail;
import com.sales.sys.exceptions.ResourceNotFoundException;
import com.sales.sys.repositories.InventoryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryDetailService {

    private InventoryDetailRepository inventoryDetailRepository;

    @Autowired
    public InventoryDetailService(InventoryDetailRepository inventoryDetailRepository) {
        this.inventoryDetailRepository = inventoryDetailRepository;
    }

    public boolean checkAvailQuantity(Long inventoryId, Long quantity) throws ResourceNotFoundException {

        InventoryDetail inventoryDetail = inventoryDetailRepository.findById(inventoryId).
                orElseThrow(() -> new ResourceNotFoundException("Inventory record not found for this id :: " + inventoryId));

        boolean checkQunatity = inventoryDetail.getAvailQuantity() >= quantity ? true : false;
        return checkQunatity;
    }
}
