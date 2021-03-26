package com.inventory.sys.controller;

import com.inventory.sys.entities.Company;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8087"})
@RequestMapping("/api/v1/inventory")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/add-company")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("/update-company")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateCompany(@RequestBody Company company) throws ResourceNotFoundException {
        Long companyId = company.getCompanyId();
        return companyService.updateCompany(companyId,company);
    }

    @DeleteMapping("/delete-company/{companyId}")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto deleteCompany(@PathVariable(value = "companyId") Long companyId) throws ResourceNotFoundException {
        return companyService.deleteCompany(companyId);
    }

    @GetMapping("/get-companies")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public List<Company> getAllCompanies(){
        return companyService.getAllCompanies();
    }

}
