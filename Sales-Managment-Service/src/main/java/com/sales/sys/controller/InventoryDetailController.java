package com.sales.sys.controller;

import com.sales.sys.exceptions.ResourceNotFoundException;
import com.sales.sys.services.InventoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/sales")
public class InventoryDetailController {

    private InventoryDetailService inventoryDetailService;

    @Autowired
    public InventoryDetailController(InventoryDetailService inventoryDetailService) {
        this.inventoryDetailService = inventoryDetailService;
    }

    @GetMapping("/item/check-quantity")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean checkInventoryQuantity(@RequestParam(name = "inventoryId") Long inventoryId, @RequestParam(name = "quantity") Long quantity) throws ResourceNotFoundException {
        return inventoryDetailService.checkAvailQuantity(inventoryId, quantity);
    }
}
