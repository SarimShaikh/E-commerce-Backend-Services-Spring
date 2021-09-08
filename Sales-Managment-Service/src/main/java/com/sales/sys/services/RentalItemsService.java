package com.sales.sys.services;

import com.sales.sys.entities.RentalItems;
import com.sales.sys.entities.ReturnItems;
import com.sales.sys.exceptions.ResourceNotFoundException;
import com.sales.sys.messageDTO.CustomResponseDto;
import com.sales.sys.messageDTO.RentalReturnItemsDTO;
import com.sales.sys.repositories.RentalItemsRepository;
import com.sales.sys.repositories.ReturnItemsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RentalItemsService {

    private RentalItemsRepository rentalItemsRepository;
    private ReturnItemsRepository returnItemsRepository;
    private ModelMapper modelMapper;

    @Autowired
    public RentalItemsService(RentalItemsRepository rentalItemsRepository, ReturnItemsRepository returnItemsRepository, ModelMapper modelMapper) {
        this.rentalItemsRepository = rentalItemsRepository;
        this.returnItemsRepository = returnItemsRepository;
        this.modelMapper = modelMapper;
    }

    public List<RentalReturnItemsDTO> getAllRentedItemsOrWithUserId(Long userId) {
        List<Map<String, Object>> rentItemsList = userId != null ? rentalItemsRepository.getRentalItemsWithUserId(userId) : rentalItemsRepository.getAllRentalItems();
        List<RentalReturnItemsDTO> rentalItemsList = getRentalReturnItemsDTOS(rentItemsList);
        return rentalItemsList;
    }

    public List<RentalReturnItemsDTO> getAllRentedItemsWithStoreId(Long storeId) {
        List<Map<String, Object>> rentItemsList =  rentalItemsRepository.getRentalItemsWithStoreId(storeId);
        List<RentalReturnItemsDTO> rentalItemsList = getRentalReturnItemsDTOS(rentItemsList);
        return rentalItemsList;
    }

    private List<RentalReturnItemsDTO> getRentalReturnItemsDTOS(List<Map<String, Object>> rentItemsList) {
        List<RentalReturnItemsDTO> rentalItemsList = new ArrayList<>();
        for (Map<String, Object> item : rentItemsList) {
            RentalReturnItemsDTO rentalReturnItemsDTO = new RentalReturnItemsDTO();
            rentalReturnItemsDTO.setRentalId(((Integer) item.get("rentalId")).longValue());
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

    public CustomResponseDto saveReturnedItems(Long rentalId) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        RentalItems rentalItems = rentalItemsRepository.findById(rentalId).
                orElseThrow(() -> new ResourceNotFoundException("Rental item not found for this id :: " + rentalId));
        ReturnItems returnItems = modelMapper.map(rentalItems, ReturnItems.class);
        returnItemsRepository.save(returnItems);
        rentalItemsRepository.delete(rentalItems);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Rented Item move successfully into returned items stock");
        return customResponseDto;
    }
}
