package com.sales.sys.services;

import com.sales.sys.messageDTO.RentalReturnItemsDTO;
import com.sales.sys.repositories.ReturnItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReturnItemsService {

    private ReturnItemsRepository returnItemsRepository;

    @Autowired
    public ReturnItemsService(ReturnItemsRepository returnItemsRepository) {
        this.returnItemsRepository = returnItemsRepository;
    }

    public List<RentalReturnItemsDTO> getAllReturnItems() {
        List<Map<String, Object>> rentItemsList = returnItemsRepository.getAllReturnItems();
        List<RentalReturnItemsDTO> rentalItemsList = new ArrayList<>();
        for (Map<String, Object> item : rentItemsList) {
            RentalReturnItemsDTO rentalReturnItemsDTO = new RentalReturnItemsDTO();
            rentalReturnItemsDTO.setItemName((String) item.get("itemName"));
            rentalReturnItemsDTO.setItemSize((String) item.get("itemSize"));
            rentalReturnItemsDTO.setItemPrice(((Integer) item.get("itemPrice")).longValue());
            rentalReturnItemsDTO.setOrderNumber((String) item.get("orderNumber"));
            rentalReturnItemsDTO.setPenaltyAmount(((Integer) item.get("penaltyAmount")).longValue());
            rentalReturnItemsDTO.setQuantity(((Integer) item.get("quantity")).longValue());
            rentalReturnItemsDTO.setFromDate(item.get("fromDate").toString());
            rentalReturnItemsDTO.setToDate(item.get("toDate").toString());
            rentalReturnItemsDTO.setOrderDate(item.get("orderDate").toString());
            rentalReturnItemsDTO.setStatus((String) item.get("status"));
            rentalItemsList.add(rentalReturnItemsDTO);
        }
        return rentalItemsList;
    }
}
