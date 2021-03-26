package com.inventory.sys.controller;

import com.inventory.sys.messageDTO.RentalItemsDTO;
import com.inventory.sys.services.apicall.RestRentalApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8082", "http://localhost:8087"})
@RequestMapping("/api/v1/inventory")
public class RentalItemsController {

    private RestRentalApi restRentalApi;

    @Autowired
    public RentalItemsController(RestRentalApi restRentalApi) {
        this.restRentalApi = restRentalApi;
    }

    @GetMapping("/get-rental-items")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<List<RentalItemsDTO>> getAllRentalItems() throws URISyntaxException {
        return restRentalApi.getAllRentalItems();
    }
}
