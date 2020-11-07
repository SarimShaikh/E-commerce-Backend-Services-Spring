package com.inventory.sys.Repositories;

import com.inventory.sys.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    boolean existsByItemName(String itemName);
}
