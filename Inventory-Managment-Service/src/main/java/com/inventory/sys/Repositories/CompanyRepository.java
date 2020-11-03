package com.inventory.sys.Repositories;

import com.inventory.sys.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
   boolean existsByCompanyName(String name);
}
