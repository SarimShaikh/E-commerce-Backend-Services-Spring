package com.inventory.sys.Repositories;

import com.inventory.sys.entities.InventoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail,Long> {

}
