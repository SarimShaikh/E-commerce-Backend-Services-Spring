package com.sales.sys.controller;

import com.sales.sys.exceptions.ResourceNotFoundException;
import com.sales.sys.messageDTO.CustomResponseDto;
import com.sales.sys.messageDTO.RentalReturnItemsDTO;
import com.sales.sys.services.RentalItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8087"})
@RequestMapping("/api/v1/sales")
public class RentalItemsController {

    private RentalItemsService rentalItemsService;

    @Autowired
    public RentalItemsController(RentalItemsService rentalItemsService) {
        this.rentalItemsService = rentalItemsService;
    }

    @GetMapping("/rental-items")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RentalReturnItemsDTO> getAllItemsInventory() {
        return rentalItemsService.getAllRentedItems();
    }

    @GetMapping("/user-rental-items/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RentalReturnItemsDTO> getAllItemsInventoryWithUserId(@PathVariable(value = "userId") Long userId) {
        return rentalItemsService.getAllRentedItemsWithUserId(userId);
    }

    @DeleteMapping("/delete-rental-item/{rentalId}")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto deleteItemWithDetails(@PathVariable(value = "rentalId") Long rentalId) throws ResourceNotFoundException {
        return rentalItemsService.saveReturnedItems(rentalId);
    }
}
