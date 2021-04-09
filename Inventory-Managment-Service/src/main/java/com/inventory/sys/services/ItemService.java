package com.inventory.sys.services;

import com.inventory.sys.Repositories.*;
import com.inventory.sys.entities.*;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceExistsException;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.messageDTO.ItemDetailsDTO;
import com.inventory.sys.messageDTO.ItemRequestDTO;
import com.inventory.sys.utils.UtilsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private ImagesRepository imagesRepository;
    private ItemDetailsRepository itemDetailsRepository;
    private InventoryDetailRepository inventoryDetailRepository;
    private StoresRepository storesRepository;
    private String filePath;
    private String downloadUrl;

    @Autowired
    public ItemService(ItemRepository itemRepository, ImagesRepository imagesRepository, ItemDetailsRepository itemDetailsRepository,
                       InventoryDetailRepository inventoryDetailRepository, StoresRepository storesRepository, @Value("${image.download.path}") String url, @Value("${images.path}") String imgPath) {
        this.itemRepository = itemRepository;
        this.imagesRepository = imagesRepository;
        this.itemDetailsRepository = itemDetailsRepository;
        this.inventoryDetailRepository = inventoryDetailRepository;
        this.storesRepository = storesRepository;
        this.downloadUrl = url;
        this.filePath = imgPath;
    }


    public void saveImages(MultipartFile file, Long itemId) {
        try {
            final Path root = Paths.get(filePath);
            String fileName = UUID.randomUUID().toString().replace("-", "") +
                    UtilsClass.getExtention(file.getOriginalFilename());
            Files.copy(file.getInputStream(), root.resolve(fileName));

            Images images = new Images();
            Item item = itemRepository.findById(itemId).
                    orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
            images.setImagePath(fileName);

            images.setItemId(item.getItemId());
            imagesRepository.save(images);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public CustomResponseDto addItem(ItemRequestDTO itemRequestDTO) throws ResourceExistsException {
        CustomResponseDto customResponseDto = new CustomResponseDto();

        if (itemRepository.existsByItemName(itemRequestDTO.getItemName())) {
            customResponseDto.setResponseCode("401");
            customResponseDto.setMessage("Item Already Exists with that name!");
            throw new ResourceExistsException("Item Already Exists with that name!");
        }

        Stores stores = storesRepository.findStoresByUserId(itemRequestDTO.getUserId());
        Item item = new Item();
        item.setStoreId(stores.getStoreId());
        item.setCompanyId(itemRequestDTO.getCompanyId());
        item.setCategoryId(itemRequestDTO.getCategoryId());
        item.setSubCategoryId(itemRequestDTO.getSubCategoryId());
        item.setItemName(itemRequestDTO.getItemName());
        item.setIsActive((byte) 1);

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

        for (ItemDetailsDTO itemDetails : itemRequestDTO.getItemDetails()) {
            ItemDetails itemDetails1 = new ItemDetails();
            itemDetails1.setItemId(item.getItemId());
            itemDetails1.setItemSize(itemDetails.getItemSize());
            itemDetails1.setItemPrice(itemDetails.getItemPrice());
            itemDetails1.setFineAmount(itemDetails.getFineAmount());
            itemDetails1.setRentalDays(itemDetails.getRentalDays());
            itemDetails1.setIsActive((byte) 1);
            itemDetailsRepository.save(itemDetails1);
            itemDetailsRepository.flush();
            inventoryDetailRepository.save(new InventoryDetail(item.getItemId(), itemDetails1.getItemDetailId(), (long) 0, (byte) 1));
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
        if (!itemRequestDTO.getItemDetails().isEmpty()) {
            for (ItemDetailsDTO itemDetailsDTO : itemRequestDTO.getItemDetails()) {
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
    public CustomResponseDto deleteItemWithDetails(Long itemId) throws ResourceNotFoundException {
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

    public CustomResponseDto deleteItemDetail(Long itemDetailId) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        ItemDetails itemDetails = itemDetailsRepository.findById(itemDetailId).
                orElseThrow(() -> new ResourceNotFoundException("Item Detail not found for this id :: " + itemDetailId));

        itemDetailsRepository.delete(itemDetails);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Item detail Deleted");
        return customResponseDto;
    }

    public CustomResponseDto deleteImage(Long imgeId) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        Images image = imagesRepository.findById(imgeId).
                orElseThrow(() -> new ResourceNotFoundException("Image not found for this id :: " + imgeId));

        String path = filePath + "//" + image.getImagePath();
        File file = new File(path);
        file.delete();

        imagesRepository.delete(image);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Image Deleted");
        return customResponseDto;
    }

    public Map<String, Object> getAllItems(String itemName, int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Item> pageItem = itemName != null ? itemRepository.getAllByItemNameStartsWith(itemName, paging) : itemRepository.findAll(paging);
        List<Item> items = pageItem.getContent();
        List<Images> imagesList;
        Collection<Images> images;
        for (Item item : items) {
            imagesList = new ArrayList<>();
            images = item.getImages();
            for (Images img : images) {
                Images images1 = new Images();
                images1.setItemId(img.getItemId());
                images1.setImageId(img.getImageId());
                images1.setImagePath(downloadUrl + img.getImagePath());
                imagesList.add(images1);
            }
            item.setImages(imagesList);

        }
        response.put("items", items);
        response.put("currentPage", pageItem.getNumber());
        response.put("totalItems", pageItem.getTotalElements());
        response.put("totalPages", pageItem.getTotalPages());

        return response;
    }

    public Map<String, Object> getAllItemsByCategoryIdAndSubCategoryId(Long categoryId, Long subCategoryId, int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Item> pageItem = itemRepository.getAllByCategoryIdOrSubCategoryId(categoryId, subCategoryId, paging);
        List<Item> items = pageItem.getContent();
        List<Images> imagesList;
        Collection<Images> images;
        for (Item item : items) {
            imagesList = new ArrayList<>();
            images = item.getImages();
            for (Images img : images) {
                Images images1 = new Images();
                images1.setItemId(img.getItemId());
                images1.setImageId(img.getImageId());
                images1.setImagePath(downloadUrl + img.getImagePath());
                imagesList.add(images1);
            }
            item.setImages(imagesList);

        }
        response.put("items", items);
        response.put("currentPage", pageItem.getNumber());
        response.put("totalItems", pageItem.getTotalElements());
        response.put("totalPages", pageItem.getTotalPages());

        return response;
    }

    public Map<String, Object> getAllItemsBystoreId(Long storeId, int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Item> pageItem = itemRepository.getAllByStoreId(storeId, paging);
        List<Item> items = pageItem.getContent();
        List<Images> imagesList;
        Collection<Images> images;
        for (Item item : items) {
            imagesList = new ArrayList<>();
            images = item.getImages();
            for (Images img : images) {
                Images images1 = new Images();
                images1.setItemId(img.getItemId());
                images1.setImageId(img.getImageId());
                images1.setImagePath(downloadUrl + img.getImagePath());
                imagesList.add(images1);
            }
            item.setImages(imagesList);

        }
        response.put("items", items);
        response.put("currentPage", pageItem.getNumber());
        response.put("totalItems", pageItem.getTotalElements());
        response.put("totalPages", pageItem.getTotalPages());

        return response;
    }

}
