package com.inventory.sys.controller;

import com.inventory.sys.entities.Images;
import com.inventory.sys.entities.Item;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.exceptions.ResponseMessage;
import com.inventory.sys.messageDto.ItemRequestDTO;
import com.inventory.sys.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @PutMapping("/update-item")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateItem(@RequestBody ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
        return itemService.updateItem(itemRequestDTO);
    }

    @PutMapping("/update-item-details")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateItemDetails(@RequestBody ItemRequestDTO itemRequestDTO) throws ResourceNotFoundException {
        return itemService.updateItemDetail(itemRequestDTO);
    }

    @DeleteMapping("/delete-itemAndDetail/{itemId}")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto deleteItemWithDetails(@PathVariable(value = "itemId") Long itemId) throws ResourceNotFoundException {
        return itemService.deleteItemWithDetails(itemId);
    }

    @DeleteMapping("/delete-item-Details/{itemDetailId}")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto deleteItemDetails(@PathVariable(value = "itemDetailId") Long itemDetailId) throws ResourceNotFoundException {
        return itemService.deleteItemDetail(itemDetailId);
    }

    @GetMapping("/get-items")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception{
        // Load file as Resource
        Resource resource ;
        try {
            Path filePath = Paths.get( "D://java-projects//e-commerce-backend-services//Inventory-Managment-Service//upload-images//"+fileName)
                    .toAbsolutePath().normalize();
             resource = new UrlResource(filePath.toUri());

        } catch (MalformedURLException ex) {
            throw new MalformedURLException("File not found " + fileName);
        }
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            throw new Exception("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
