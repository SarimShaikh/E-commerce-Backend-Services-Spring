package com.inventory.sys.Repositories;

import com.inventory.sys.entities.InventoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail,Long> {

    @Query(value = "SELECT com.COMPANY_NAME companyName, item.ITEM_ID itemId,item.ITEM_NAME itemName , detail.ITEM_DETAIL_ID itemDetailId, detail.ITEM_SIZE itemSize, detail.ITEM_PRICE itemPrice,\n" +
            "detail.FINE_AMOUNT fineAmount,detail.RENTAL_DAYS rentalDays, inventory.INVENTORY_ID inventoryId , inventory.AVAILABLE_QUANTITY availableQuan FROM company com JOIN items item\n" +
            "ON(com.COMPANY_ID = item.COMPANY_ID)JOIN inventory_detail inventory \n" +
            "ON(item.ITEM_ID = inventory.ITEM_ID) JOIN items_detail detail\n" +
            "ON(detail.ITEM_DETAIL_ID = inventory.ITEM_DETAIL_ID)", nativeQuery = true)
    List<Map<String, Object>> getItemsInventory();
}
