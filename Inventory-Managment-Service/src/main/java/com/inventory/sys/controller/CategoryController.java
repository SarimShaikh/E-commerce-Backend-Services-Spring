package com.inventory.sys.controller;

import com.inventory.sys.entities.Category;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceExistsException;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.messageDTO.CategoryRequestDTO;
import com.inventory.sys.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8087"})
@RequestMapping("/api/v1/inventory")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add-category")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addCategory(@RequestBody Category category) throws ResourceExistsException {
        return categoryService.addCategory(category);
    }

    @PostMapping("/add-sub-category")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addSubCategory(@RequestBody Category category) throws Exception {
        return categoryService.addSubCategory(category);
    }

    @PutMapping("/update-category")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateCategory(@RequestBody CategoryRequestDTO categoryRequestDto) throws ResourceNotFoundException {
        Long categoryId = categoryRequestDto.getCategoryId();
        return categoryService.updateCategory(categoryRequestDto);
    }

    @PutMapping("/update-Subcategory")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateSubCategory(@RequestBody CategoryRequestDTO categoryRequestDto) throws ResourceNotFoundException {
        return categoryService.updateSubCategories(categoryRequestDto);
    }

    @DeleteMapping("/delete-category/{categoryId}")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto deleteCategory(@PathVariable(value = "categoryId") Long categoryId) throws ResourceNotFoundException {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/get-categories")
    //@PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/get-mob-categories")
    public Map<String,Object> getAllCategoriesForMobile(){
        Map<String,Object> categoriesMap = new HashMap<>();
        categoriesMap.put("categories",categoryService.getAllCategories());
        return categoriesMap;
    }
}
