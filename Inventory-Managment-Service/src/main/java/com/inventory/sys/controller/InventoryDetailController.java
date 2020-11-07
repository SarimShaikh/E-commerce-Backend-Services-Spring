package com.inventory.sys.controller;

import com.inventory.sys.messageDto.InventoryDTO;
import com.inventory.sys.services.InventoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/inventory")
public class InventoryDetailController {

    private InventoryDetailService inventoryDetailService;

    @Autowired
    public InventoryDetailController(InventoryDetailService inventoryDetailService) {
        this.inventoryDetailService = inventoryDetailService;
    }

    @GetMapping("/get-inventory")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public List<InventoryDTO> getAllInventoryItems(){
        return inventoryDetailService.getAllItemsInventory();
    }
}
