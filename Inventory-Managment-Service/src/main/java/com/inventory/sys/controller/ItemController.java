package com.inventory.sys.controller;

import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.exceptions.ResponseMessage;
import com.inventory.sys.messageDto.ItemRequestDTO;
import com.inventory.sys.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8082"})
@RequestMapping("/api/v1/inventory")
public class ItemController {

    private ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/upload-images/{itemId}")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files ,
                                                       @PathVariable(value = "itemId") Long itemId) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {
                itemService.saveImages(file,itemId);
                fileNames.add(file.getOriginalFilename());
            });

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/add-item")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addItem(@RequestBody ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
        return itemService.addItem(itemRequestDTO);
    }

    @PostMapping("/add-item-details")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addItemDetails(@RequestBody ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
        return itemService.addItemDetail(itemRequestDTO);
    }
}
