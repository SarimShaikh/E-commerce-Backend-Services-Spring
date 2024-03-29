package com.inventory.sys.messageDTO;

import java.io.Serializable;
import java.util.Collection;

public class ItemRequestDTO implements Serializable {

    private Long userId;
    private Long itemId;
    private Long companyId;
    private Long categoryId;
    private Long subCategoryId;
    private String itemName;
    private Collection<ItemDetailsDTO> itemDetails;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Collection<ItemDetailsDTO> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(Collection<ItemDetailsDTO> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
