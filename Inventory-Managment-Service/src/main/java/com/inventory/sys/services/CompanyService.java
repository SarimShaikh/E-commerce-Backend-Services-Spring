package com.inventory.sys.services;

import com.inventory.sys.Repositories.CompanyRepository;
import com.inventory.sys.entities.Company;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceExistsException;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CustomResponseDto addCompany(Company company) throws ResourceExistsException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        if (companyRepository.existsByCompanyName(company.getCompanyName())) {
            throw new ResourceExistsException("Company Already Exists with that name!");
        }
        company.setIsActive((byte) 1);
        final Company company1 = companyRepository.save(company);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Company added successfully");
        customResponseDto.setEntityClass(company1);
        return customResponseDto;
    }

    public CustomResponseDto updateCompany(Long companyId, Company companyReq) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyReq.getCompanyId()));
        company.setCompanyId(companyReq.getCompanyId());
        company.setCompanyName(companyReq.getCompanyName());
        company.setCompanyEmail(companyReq.getCompanyEmail());
        company.setCompanyContact(companyReq.getCompanyContact());
        company.setIsActive(companyReq.getIsActive());

        final Company updatedCompany = companyRepository.save(company);

        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Company Updated");
        customResponseDto.setEntityClass(updatedCompany);
        return customResponseDto;
    }

    public CustomResponseDto deleteCompany(Long companyId) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));
        companyRepository.delete(company);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Company Deleted");
        return customResponseDto;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
