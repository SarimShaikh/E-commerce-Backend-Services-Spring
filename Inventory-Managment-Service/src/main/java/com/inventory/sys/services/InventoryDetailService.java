package com.inventory.sys.services;

import com.inventory.sys.Repositories.InventoryDetailRepository;
import com.inventory.sys.messageDto.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InventoryDetailService {

    private InventoryDetailRepository inventoryDetailRepository;

    @Autowired
    public InventoryDetailService(InventoryDetailRepository inventoryDetailRepository) {
        this.inventoryDetailRepository = inventoryDetailRepository;
    }

    public List<InventoryDTO> getAllItemsInventory() {
        List<Map<String, Object>> inventorylist = inventoryDetailRepository.getItemsInventory();
        List<InventoryDTO> inventoryDTOList = new ArrayList<>();

        for (Map<String, Object> item : inventorylist) {
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setItemId(((Integer) item.get("itemId")).longValue());
            inventoryDTO.setItemDetailId(((Integer) item.get("itemDetailId")).longValue());
            inventoryDTO.setInventoryId(((Integer) item.get("inventoryId")).longValue());
            inventoryDTO.setCompanyName((String) item.get("companyName"));
            inventoryDTO.setItemName((String) item.get("itemName"));
            inventoryDTO.setItemSize((String) item.get("itemSize"));
            inventoryDTO.setItemPrice(((Integer) item.get("itemPrice")).longValue());
            inventoryDTO.setFineAmount(((Integer) item.get("fineAmount")).longValue());
            inventoryDTO.setRentalDays(((Integer) item.get("rentalDays")).longValue());
            inventoryDTO.setAvailableQuan(((Integer) item.get("availableQuan")).longValue());
            inventoryDTOList.add(inventoryDTO);
        }
        return  inventoryDTOList;
    }
}