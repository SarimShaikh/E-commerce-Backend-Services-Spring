package com.sales.sys.repositories;

import com.sales.sys.entities.ReturnItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnItemsRepository extends JpaRepository<ReturnItems,Long> {
}
