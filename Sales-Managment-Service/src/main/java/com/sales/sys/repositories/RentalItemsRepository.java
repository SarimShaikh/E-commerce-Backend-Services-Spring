package com.sales.sys.repositories;

import com.sales.sys.entities.RentalItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RentalItemsRepository extends JpaRepository<RentalItems, Long> {

    @Query(value = "SELECT item.ITEM_NAME itemName, detail.ITEM_SIZE itemSize, detail.ITEM_PRICE itemPrice, rent_item.ORDER_NO orderNumber, rent_item.PENALTY_AMOUNT penaltyAmount,\n" +
            "rent_item.FROM_DATE fromDate, rent_item.TO_DATE toDate, rent_item.QUANTITY quantity, rent_item.STATUS STATUS, rent_item.CREATED_DATE orderDate \n" +
            "FROM items item JOIN items_detail detail ON(item.ITEM_ID = detail.ITEM_ID) JOIN rental_items rent_item\n" +
            "ON(detail.ITEM_ID = rent_item.ITEM_DETAIL_ID)", nativeQuery = true)
    List<Map<String, Object>> getAllRentalItems();

    @Query(value = "SELECT item.ITEM_NAME itemName, detail.ITEM_SIZE itemSize, detail.ITEM_PRICE itemPrice, rent_item.ORDER_NO orderNumber, rent_item.PENALTY_AMOUNT penaltyAmount,\n" +
            "rent_item.FROM_DATE fromDate, rent_item.TO_DATE toDate, rent_item.QUANTITY quantity, rent_item.STATUS STATUS, rent_item.CREATED_DATE orderDate \n" +
            "FROM items item JOIN items_detail detail ON(item.ITEM_ID = detail.ITEM_ID) JOIN rental_items rent_item\n" +
            "ON(detail.ITEM_ID = rent_item.ITEM_DETAIL_ID) WHERE rent_item.USER_ID = ?1", nativeQuery = true)
    List<Map<String, Object>> getRentalItemsWithUserId(Long userId);


}
