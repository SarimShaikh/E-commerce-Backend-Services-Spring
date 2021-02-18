package com.inventory.sys.Repositories;

import com.inventory.sys.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByItemName(String itemName);

    Page<Item> getAllByItemNameStartsWith(String itemName, Pageable pageable);

    Page<Item> getAllByCategoryIdOrSubCategoryId(Long categoryId, Long subCategoryId, Pageable pageable);

    Page<Item> getAllByStoreId(Long storeId, Pageable pageable);

    @Override
    Page<Item> findAll(Pageable pageable);

    @Override
    boolean existsById(Long itemId);
}
