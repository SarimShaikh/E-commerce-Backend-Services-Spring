package com.inventory.sys.Repositories;

import com.inventory.sys.entities.Stores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoresRepository extends JpaRepository<Stores, Long> {
    boolean existsByStoreName(String storeName);

    boolean existsByStoreRegistrationNumber(String registrationNumber);

    List<Stores> getAllByStoreNameStartsWith(String storeName, Pageable pageable);

    Stores findStoresByUserId(Long userId);

    @Override
    Page<Stores> findAll(Pageable pageable);
}
