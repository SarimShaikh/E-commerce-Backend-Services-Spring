package com.inventory.sys.services;

import com.inventory.sys.Repositories.ImagesRepository;
import com.inventory.sys.Repositories.InventoryDetailRepository;
import com.inventory.sys.Repositories.ItemDetailsRepository;
import com.inventory.sys.Repositories.ItemRepository;
import com.inventory.sys.entities.Images;
import com.inventory.sys.entities.InventoryDetail;
import com.inventory.sys.entities.Item;
import com.inventory.sys.entities.ItemDetails;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.messageDto.ItemDetailsDTO;
import com.inventory.sys.messageDto.ItemRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ImagesRepository imagesRepository;
    private ItemDetailsRepository itemDetailsRepository;
    private InventoryDetailRepository inventoryDetailRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository , ImagesRepository imagesRepository , ItemDetailsRepository itemDetailsRepository, InventoryDetailRepository inventoryDetailRepository) {
        this.itemRepository = itemRepository;
        this.imagesRepository = imagesRepository;
        this.itemDetailsRepository = itemDetailsRepository;
        this.inventoryDetailRepository = inventoryDetailRepository;

    }

    private final Path root = Paths.get("D://java-projects//e-commerce-backend-services//Inventory-Managment-Service//upload-images");

    public void saveImages(MultipartFile file , Long itemId) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            Path file1 = root.resolve(file.getOriginalFilename());
            Resource resource = new UrlResource(file1.toUri());

            Images images = new Images();
            Item item = itemRepository.findById(itemId).
                    orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
            images.setImagePath(this.root.resolve(file.getOriginalFilename()).toString());

            images.setItemId(item.getItemId());
            imagesRepository.save(images);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public CustomResponseDto addItem(ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
            CustomResponseDto customResponseDto = new CustomResponseDto();
        if(itemRepository.existsByItemName(itemRequestDTO.getItemName())){
            customResponseDto.setResponseCode("401");
            customResponseDto.setMessage("Item Already Exists with that name!");
            throw  new ResourceNotFoundException("Item Already Exists with that name!");
        }

        Item item =new Item();
        item.setCompanyId(itemRequestDTO.getCompanyId());
        item.setCategoryId(itemRequestDTO.getCategoryId());
        item.setSubCategoryId(itemRequestDTO.getSubCategoryId());
        item.setItemName(itemRequestDTO.getItemName());
        item.setIsActive((byte)1);

        final Item item1 = itemRepository.save(item);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Item added successfully");
        customResponseDto.setEntityClass(item1);
        return customResponseDto;
    }

    @Transactional
    public CustomResponseDto addItemDetail(ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();

        Item item = itemRepository.findById(itemRequestDTO.getItemId()).
                orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemRequestDTO.getItemId()));

        for(ItemDetailsDTO itemDetails : itemRequestDTO.getItemDetails()){
            ItemDetails itemDetails1 = new ItemDetails();
            itemDetails1.setItemId(item.getItemId());
            itemDetails1.setItemSize(itemDetails.getItemSize());
            itemDetails1.setItemPrice(itemDetails.getItemPrice());
            itemDetails1.setFineAmount(itemDetails.getFineAmount());
            itemDetails1.setRentalDays(itemDetails.getRentalDays());
            itemDetails1.setIsActive((byte)1);
            itemDetailsRepository.save(itemDetails1);
            itemDetailsRepository.flush();
            inventoryDetailRepository.save(new InventoryDetail(item.getItemId(),itemDetails1.getItemDetailId(),(long)0,(byte)1));
        }
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Item Details added successfully");
        return customResponseDto;
    }

    public CustomResponseDto updateItem(ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();

        Item item = itemRepository.findById(itemRequestDTO.getItemId()).
                orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemRequestDTO.getItemId()));

        item.setCompanyId(itemRequestDTO.getCompanyId());
        item.setCategoryId(itemRequestDTO.getCategoryId());
        item.setSubCategoryId(itemRequestDTO.getSubCategoryId());
        item.setItemName(itemRequestDTO.getItemName());
        final Item updatedItem = itemRepository.save(item);

        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Item Updated successfully");
        customResponseDto.setEntityClass(updatedItem);
        return customResponseDto;
    }

    public CustomResponseDto updateItemDetail(ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        if(!itemRequestDTO.getItemDetails().isEmpty()){
            for(ItemDetailsDTO itemDetailsDTO : itemRequestDTO.getItemDetails()){
                ItemDetails itemDetails = itemDetailsRepository.findById(itemDetailsDTO.getItemDetailId()).
                        orElseThrow(() -> new ResourceNotFoundException("Item Detail not found for this id :: " + itemDetailsDTO.getItemDetailId()));

                itemDetails.setItemDetailId(itemDetailsDTO.getItemDetailId());
                itemDetails.setItemId(itemDetails.getItemId());
                itemDetails.setItemSize(itemDetailsDTO.getItemSize());
                itemDetails.setItemPrice(itemDetailsDTO.getItemPrice());
                itemDetails.setFineAmount(itemDetailsDTO.getFineAmount());
                itemDetails.setRentalDays(itemDetailsDTO.getRentalDays());
                itemDetails.setIsActive(itemDetailsDTO.getIsActive());
                itemDetailsRepository.save(itemDetails);
            }
            customResponseDto.setResponseCode("200");
            customResponseDto.setMessage("Item Details updated successfully");
        }
        return customResponseDto;
    }

    @Transactional
    public CustomResponseDto deleteItemWithDetails(Long itemId) throws ResourceNotFoundException{
        CustomResponseDto customResponseDto = new CustomResponseDto();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
        itemRepository.delete(item);
        itemDetailsRepository.deleteAllByItemId(itemId);
        imagesRepository.deleteAllByItemId(itemId);
        inventoryDetailRepository.deleteAllByItemId(itemId);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Item and details Deleted");
        return customResponseDto;
    }

    public CustomResponseDto deleteItemDetail(Long itemDetailId) throws ResourceNotFoundException{
        CustomResponseDto customResponseDto = new CustomResponseDto();
        ItemDetails itemDetails = itemDetailsRepository.findById(itemDetailId).
                orElseThrow(() -> new ResourceNotFoundException("Item Detail not found for this id :: " + itemDetailId));

        itemDetailsRepository.delete(itemDetails);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Item detail Deleted");
        return customResponseDto;
    }
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

}
