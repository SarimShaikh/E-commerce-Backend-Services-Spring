package com.inventory.sys.messageDto;

import com.inventory.sys.entities.SubCategory;

import java.io.Serializable;
import java.util.Collection;

public class CategoryRequestDTO implements Serializable {

    private Long categoryId;
    private String categoryType;
    private Collection<SubCategory> subCategories;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Collection<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Collection<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

}
