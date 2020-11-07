package com.inventory.sys.services;

import com.inventory.sys.Repositories.CategoryRepository;
import com.inventory.sys.Repositories.ImagesRepository;
import com.inventory.sys.Repositories.ItemDetailsRepository;
import com.inventory.sys.Repositories.ItemRepository;
import com.inventory.sys.entities.Images;
import com.inventory.sys.entities.Item;
import com.inventory.sys.entities.ItemDetails;
import com.inventory.sys.entities.SubCategory;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.messageDto.ItemDetailsDTO;
import com.inventory.sys.messageDto.ItemRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ImagesRepository imagesRepository;
    private ItemDetailsRepository itemDetailsRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository , ImagesRepository imagesRepository , ItemDetailsRepository itemDetailsRepository) {
        this.itemRepository = itemRepository;
        this.imagesRepository = imagesRepository;
        this.itemDetailsRepository = itemDetailsRepository;
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
        }
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Item Details added successfully");
        return customResponseDto;
    }
}
