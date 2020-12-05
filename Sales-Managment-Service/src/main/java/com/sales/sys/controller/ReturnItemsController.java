package com.sales.sys.controller;

import com.sales.sys.messageDTO.RentalReturnItemsDTO;
import com.sales.sys.services.ReturnItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
}
