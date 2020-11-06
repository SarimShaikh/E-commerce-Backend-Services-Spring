package com.inventory.sys.controller;

import com.inventory.sys.entities.Category;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.messageDto.RequestDto;
import com.inventory.sys.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/inventory")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add-category")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addCategory(@RequestBody Category category) throws ResourceNotFoundException {
        return categoryService.addCategory(category);
    }

    @PostMapping("/add-sub-category")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto addSubCategory(@RequestBody Category category) throws ResourceNotFoundException {
        return categoryService.addSubCategory(category);
    }

    @PutMapping("/update-category")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateCategory(@RequestBody RequestDto requestDto) throws ResourceNotFoundException {
        return categoryService.updateCategory(requestDto);
    }

    @PutMapping("/update-Subcategory")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto updateSubCategory(@RequestBody RequestDto requestDto) throws ResourceNotFoundException {
        return categoryService.updateSubCategories(requestDto);
    }

    @DeleteMapping("/delete-category/{categoryId}")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public CustomResponseDto deleteCategory(@PathVariable(value = "categoryId") Long categoryId) throws ResourceNotFoundException {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/get-categories")
    @PreAuthorize("hasRole('SUB_ADMIN') or hasRole('ADMIN')")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
