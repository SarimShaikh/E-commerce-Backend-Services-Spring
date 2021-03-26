package com.inventory.sys.controller;

import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.messageDTO.InventoryDTO;
import com.inventory.sys.services.InventoryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8087"})
@RequestMapping("/api/v1/inventory")
public class InventoryDetailController {

    private InventoryDetailService inventoryDetailService;

    @Autowired
    public InventoryDetailController(InventoryDetailService inventoryDetailService) {
        this.inventoryDetailService = inventoryDetailService;
    }

    @GetMapping("/get-inventory")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public List<InventoryDTO> getAllInventoryItems() {
        return inventoryDetailService.getAllItemsInventory();
    }

    @PutMapping("/update-inventory")
    @PreAuthorize("hasRole('ADMIN')")
    public CustomResponseDto updateInventoryDetail(@RequestBody InventoryDTO inventoryDTO) throws ResourceNotFoundException {
        return inventoryDetailService.updateInventory(inventoryDTO);
    }

}
