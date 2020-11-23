package com.sales.sys.repositories;

import com.sales.sys.entities.RentalItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalItemsRepository extends JpaRepository<RentalItems,Long> {
}
