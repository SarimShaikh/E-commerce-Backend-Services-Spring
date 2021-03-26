package com.inventory.sys.controller;

import com.inventory.sys.entities.Stores;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.exceptions.ResponseMessage;
import com.inventory.sys.services.StoresService;
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
import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8082"})
@RequestMapping("/api/v1/inventory")
public class StoresController {

    private StoresService storesService;

    @Autowired
    public StoresController(StoresService storesService) {
        this.storesService = storesService;
    }

    @PostMapping("/upload-image/{storeId}")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("file") MultipartFile file,
                                                       @PathVariable(value = "storeId") Long storeId) {
        String message = "";
        try {
            storesService.saveStoreImage(file, storeId);
            message = "Uploaded the files successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/add-store")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addItem(@RequestBody Stores stores) throws ResourceNotFoundException {
        return storesService.addStore(stores);
    }

    @PutMapping("/update-store")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateItem(@RequestBody Stores stores) throws ResourceNotFoundException {
        return storesService.updateStore(stores);
    }

    @DeleteMapping("/delete-store/{storeId}")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto deleteItemWithDetails(@PathVariable(value = "storeId") Long storeId) throws ResourceNotFoundException {
        return storesService.deleteStore(storeId);
    }

    @GetMapping("/get-stores")
    public List<Stores> getAllStores(){
        return storesService.getAllStores();
    }
}
