package com.sales.sys.repositories;

import com.sales.sys.entities.InventoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail,Long> {
     InventoryDetail findByItemDetailId(Long itemDetailId);
}
