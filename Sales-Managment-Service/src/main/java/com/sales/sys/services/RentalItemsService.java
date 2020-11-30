package com.sales.sys.services;

import com.sales.sys.messageDTO.RentalItemsDTO;
import com.sales.sys.repositories.RentalItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RentalItemsService {

    private RentalItemsRepository rentalItemsRepository;

    @Autowired
    public RentalItemsService(RentalItemsRepository rentalItemsRepository) {
        this.rentalItemsRepository = rentalItemsRepository;
    }

    public List<RentalItemsDTO> getAllItemsInventory() {
        List<Map<String, Object>> rentItemsList = rentalItemsRepository.getAllRentalItems();
        List<RentalItemsDTO> rentalItemsList = new ArrayList<>();
        for (Map<String, Object> item : rentItemsList) {
            RentalItemsDTO rentalItemsDTO = new RentalItemsDTO();
            rentalItemsDTO.setItemName((String) item.get("itemName"));
            rentalItemsDTO.setItemSize((String) item.get("itemSize"));
            rentalItemsDTO.setItemPrice(((Integer) item.get("itemPrice")).longValue());
            rentalItemsDTO.setOrderNumber((String) item.get("orderNumber"));
            rentalItemsDTO.setPenaltyAmount(((Integer) item.get("penaltyAmount")).longValue());
            rentalItemsDTO.setQuantity(((Integer) item.get("quantity")).longValue());
            rentalItemsDTO.setFromDate(item.get("fromDate").toString());
            rentalItemsDTO.setToDate(item.get("toDate").toString());
            rentalItemsDTO.setOrderDate(item.get("orderDate").toString());
            rentalItemsDTO.setStatus((String) item.get("status"));
            rentalItemsList.add(rentalItemsDTO);
        }
        return rentalItemsList;
    }

    public List<RentalItemsDTO> getAllItemsInventoryWithUserId(Long userId) {
        List<Map<String, Object>> rentItemsList = rentalItemsRepository.getRentalItemsWithUserId(userId);
        List<RentalItemsDTO> rentalItemsList = new ArrayList<>();
        for (Map<String, Object> item : rentItemsList) {
            RentalItemsDTO rentalItemsDTO = new RentalItemsDTO();
            rentalItemsDTO.setItemName((String) item.get("itemName"));
            rentalItemsDTO.setItemSize((String) item.get("itemSize"));
            rentalItemsDTO.setItemPrice(((Integer) item.get("itemPrice")).longValue());
            rentalItemsDTO.setOrderNumber((String) item.get("orderNumber"));
            rentalItemsDTO.setPenaltyAmount(((Integer) item.get("penaltyAmount")).longValue());
            rentalItemsDTO.setQuantity(((Integer) item.get("quantity")).longValue());
            rentalItemsDTO.setFromDate(item.get("fromDate").toString());
            rentalItemsDTO.setToDate(item.get("toDate").toString());
            rentalItemsDTO.setOrderDate(item.get("orderDate").toString());
            rentalItemsDTO.setStatus((String) item.get("status"));
            rentalItemsList.add(rentalItemsDTO);
        }
        return rentalItemsList;
    }
}
