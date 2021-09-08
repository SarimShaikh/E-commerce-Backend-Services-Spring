package com.sales.sys.repositories;

import com.sales.sys.entities.ReturnItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReturnItemsRepository extends JpaRepository<ReturnItems, Long> {

    @Query(value = "SELECT item.ITEM_NAME itemName, detail.ITEM_SIZE itemSize, detail.ITEM_PRICE itemPrice, return_item.ORDER_NO orderNumber, return_item.PENALTY_AMOUNT penaltyAmount,\n" +
            "return_item.FROM_DATE fromDate, return_item.TO_DATE toDate, return_item.QUANTITY quantity, return_item.STATUS STATUS, return_item.CREATED_DATE orderDate \n" +
            "FROM items item JOIN items_detail detail ON(item.ITEM_ID = detail.ITEM_ID) JOIN return_items return_item\n" +
            "ON(detail.ITEM_DETAIL_ID = return_item.ITEM_DETAIL_ID)", nativeQuery = true)
    List<Map<String, Object>> getAllReturnItems();

    @Query(value = "SELECT item.ITEM_NAME itemName, detail.ITEM_SIZE itemSize, detail.ITEM_PRICE itemPrice, return_item.ORDER_NO orderNumber, return_item.PENALTY_AMOUNT penaltyAmount,\n" +
            "return_item.FROM_DATE fromDate, return_item.TO_DATE toDate, return_item.QUANTITY quantity, return_item.STATUS STATUS, return_item.CREATED_DATE orderDate \n" +
            "FROM items item JOIN items_detail detail ON(item.ITEM_ID = detail.ITEM_ID) JOIN return_items return_item\n" +
            "ON(detail.ITEM_DETAIL_ID = return_item.ITEM_DETAIL_ID) WHERE return_item.STORE_ID = ?1", nativeQuery = true)
    List<Map<String, Object>> getAllReturnItemsWithStoreId(Long storeId);
}
