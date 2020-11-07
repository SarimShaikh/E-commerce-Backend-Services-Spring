package com.inventory.sys.services;

import com.inventory.sys.Repositories.CategoryRepository;
import com.inventory.sys.Repositories.SubCategoryReposiotry;
import com.inventory.sys.entities.Category;
import com.inventory.sys.entities.SubCategory;
import com.inventory.sys.exceptions.CustomResponseDto;
import com.inventory.sys.exceptions.ResourceNotFoundException;
import com.inventory.sys.messageDto.CategoryRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private SubCategoryReposiotry subCategoryReposiotry;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, SubCategoryReposiotry subCategoryReposiotry) {
        this.categoryRepository = categoryRepository;
        this.subCategoryReposiotry = subCategoryReposiotry;
    }

    public CustomResponseDto addCategory(Category category) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        if(categoryRepository.existsByCategoryType(category.getCategoryType())){
            customResponseDto.setResponseCode("401");
            customResponseDto.setMessage("Category Already Exists with that name!");
            throw  new ResourceNotFoundException("Category Already Exists with that name!");
        }

        category.setIsActive((byte)1);
        final Category category1 = categoryRepository.save(category);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Category added successfully");
        //customResponseDto.setEntityClass(category1);
        return customResponseDto;
    }

    public CustomResponseDto addSubCategory(Category category) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();

        for(SubCategory item : category.getSubCategories()){
            if(subCategoryReposiotry.existsBySubCategoryType(item.getSubCategoryType())){
                customResponseDto.setResponseCode("401");
                customResponseDto.setMessage("SubCategory Already Exists with that name!");
                throw  new ResourceNotFoundException("Fail! -> SubCategory Already Exists with that name!");
            }
        }
        Category category1 = categoryRepository.findById(category.getCategoryId()).
                orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + category.getCategoryId()));

        for(SubCategory subCategory : category.getSubCategories()){
            SubCategory subCategory1 = new SubCategory();
            subCategory1.setCategoryId(category1.getCategoryId());
            subCategory1.setSubCategoryType(subCategory.getSubCategoryType());
            subCategory1.setIsActive((byte)1);
            subCategoryReposiotry.save(subCategory1);
        }

        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Sub-Category added successfully");
        //customResponseDto.setEntityClass(category1);
        return customResponseDto;
    }

    @Transactional
    public CustomResponseDto updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDto) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryRequestDto.getCategoryId()));

        category.setCategoryType(categoryRequestDto.getCategoryType());
        final Category updatedCategory = categoryRepository.save(category);

        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Category updated successfully");
        customResponseDto.setEntityClass(updatedCategory);
        return customResponseDto;
    }

    public CustomResponseDto updateSubCategories(CategoryRequestDTO categoryRequestDto) throws ResourceNotFoundException {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        if(!categoryRequestDto.getSubCategories().isEmpty()) {
            for (SubCategory subCategory : categoryRequestDto.getSubCategories()) {
                SubCategory subCategory1 = subCategoryReposiotry.findById(subCategory.getSubCategoryId()).
                        orElseThrow(() -> new ResourceNotFoundException("Sub-Category not found for this id :: " + categoryRequestDto.getCategoryId()));
                subCategory1.setSubCategoryType(subCategory.getSubCategoryType());
                subCategoryReposiotry.save(subCategory1);
            }
            customResponseDto.setResponseCode("200");
            customResponseDto.setMessage("Category updated successfully");
        }
        return customResponseDto;
    }

    @Transactional
    public CustomResponseDto deleteCategory(Long categoryId) throws ResourceNotFoundException{
        CustomResponseDto customResponseDto = new CustomResponseDto();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + categoryId));
        categoryRepository.delete(category);
        subCategoryReposiotry.deleteAllByCategoryId(categoryId);
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("category Deleted");
        return customResponseDto;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
