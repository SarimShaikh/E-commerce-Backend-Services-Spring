package com.sales.sys.controller;

import com.sales.sys.exceptions.ResourceNotFoundException;
import com.sales.sys.services.InventoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8087"})
@RequestMapping("/api/v1/sales")
public class InventoryDetailController {

    private InventoryDetailService inventoryDetailService;

    @Autowired
    public InventoryDetailController(InventoryDetailService inventoryDetailService) {
        this.inventoryDetailService = inventoryDetailService;
    }

    @GetMapping("/check-quantity")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public boolean checkInventoryQuantity(@RequestParam(name = "itemDetailId") Long itemDetailId, @RequestParam(name = "quantity") Long quantity) throws ResourceNotFoundException {
        return inventoryDetailService.checkAvailQuantity(itemDetailId, quantity);
    }
}
