package com.inventory.sys.services;

import com.inventory.sys.Repositories.StoresRepository;
import com.inventory.sys.entities.Stores;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.utils.UtilsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StoresService {

    private StoresRepository storesRepository;
    private String filePath;
    private String downloadUrl;

    @Autowired
    public StoresService(StoresRepository storesRepository, @Value("${image.download.mob.path}") String url, @Value("${images.path}") String imgPath) {
        this.storesRepository = storesRepository;
        this.filePath = imgPath;
        this.downloadUrl = url;
    }

    public void saveStoreImage(MultipartFile file, Long storeId) {
        try {
            final Path root = Paths.get(filePath);
            String fileName = UUID.randomUUID().toString().replace("-", "") +
                    UtilsClass.getExtention(file.getOriginalFilename());
            Files.copy(file.getInputStream(), root.resolve(fileName));

            Stores store = storesRepository.findById(storeId).
                    orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + storeId));
            store.setImagePath(fileName);
            storesRepository.save(store);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public CustomResponseDto addStore(Stores stores) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();

        if (storesRepository.existsByStoreName(stores.getStoreName())) {
            customResponseDto.setResponseCode("401");
            customResponseDto.setMessage("Store Already Exists with that name!");
            throw new ResourceNotFoundException("Store Already Exists with that name!");
        }

        if (storesRepository.existsByStoreRegistrationNumber(stores.getStoreRegistrationNumber())) {
            customResponseDto.setResponseCode("401");
            customResponseDto.setMessage("Store Already Exists with that Registration number!");
            throw new ResourceNotFoundException("Store Already Exists with that Registration number!");
        }

        final Stores stores1 = storesRepository.save(stores);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Store Registered successfully");
        customResponseDto.setEntityClass(stores1);
        return customResponseDto;
    }

    public CustomResponseDto updateStore(Stores stores) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();

        Stores stores1 = storesRepository.findById(stores.getStoreId()).
                orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + stores.getStoreId()));

        if (storesRepository.existsByStoreName(stores.getStoreName())) {
            customResponseDto.setResponseCode("401");
            customResponseDto.setMessage("Store Already Exists with that name!");
            throw new ResourceNotFoundException("Store Already Exists with that name!");
        }

        stores1.setStoreName(stores.getStoreName());
        stores1.setStoreRegistrationNumber(stores.getStoreRegistrationNumber());
        stores1.setStoreContact(stores.getStoreContact());
        stores1.setStoreAddress(stores.getStoreAddress());

        final Stores updatedStore = storesRepository.save(stores1);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Store Updated Successfully");
        customResponseDto.setEntityClass(updatedStore);
        return customResponseDto;
    }

    public CustomResponseDto deleteStore(Long storeId) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();

        Stores stores = storesRepository.findById(storeId).
                orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + storeId));
        storesRepository.delete(stores);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Image Deleted");
        return customResponseDto;
    }

    public Stores getStoreByUserId(Long userId){
        return storesRepository.findStoresByUserId(userId);
    }

    public List<Stores> getAllStores() {
        List<Stores> storesList = storesRepository.findAll();
        for (int i = 0; i < storesList.size(); i++) {
            String path = downloadUrl+storesList.get(i).getImagePath();
            storesList.get(i).setImagePath(path);
        }
        return storesList;
    }
}
