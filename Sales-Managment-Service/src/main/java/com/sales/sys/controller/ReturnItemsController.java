package com.sales.sys.controller;

import com.sales.sys.messageDTO.RentalReturnItemsDTO;
import com.sales.sys.services.ReturnItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8087"})
@RequestMapping("/api/v1/sales")
public class ReturnItemsController {

    private ReturnItemsService returnItemsService;

    @Autowired
    public ReturnItemsController(ReturnItemsService returnItemsService) {
        this.returnItemsService = returnItemsService;
    }

    @GetMapping("/return-items")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public List<RentalReturnItemsDTO> getAllReturnItems() {
        return returnItemsService.getAllReturnItems();
    }

    @GetMapping("/return-store-items")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RentalReturnItemsDTO> getAllReturnItemsWithStoreId(@RequestParam(name = "storeId") Long storeId) {
        return returnItemsService.getAllReturnItemsWithStoreId(storeId);
    }
}
