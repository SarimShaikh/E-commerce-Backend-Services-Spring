package com.sales.sys.controller;

import com.sales.sys.messageDTO.RentalItemsDTO;
import com.sales.sys.services.RentalItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/sales")
public class RentalItemsController {

    private RentalItemsService rentalItemsService;

    @Autowired
    public RentalItemsController(RentalItemsService rentalItemsService) {
        this.rentalItemsService = rentalItemsService;
    }

    @GetMapping("/rental-items")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RentalItemsDTO> getAllItemsInventory() {
        return rentalItemsService.getAllItemsInventory();
    }

    @GetMapping("/user-rental-items/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RentalItemsDTO> getAllItemsInventoryWithUserId(@PathVariable(value = "userId") Long userId) {
        return rentalItemsService.getAllItemsInventoryWithUserId(userId);
    }
}
