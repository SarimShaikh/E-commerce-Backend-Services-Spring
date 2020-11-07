package com.inventory.sys.messageDto;

import java.io.Serializable;

public class InventoryDTO implements Serializable {

    private Long itemId;
    private Long itemDetailId;
    private Long inventoryId;
    private String companyName;
    private String itemName;
    private String itemSize;
    private Long itemPrice;
    private Long fineAmount;
    private Long rentalDays;
    private Long availableQuan;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemDetailId() {
        return itemDetailId;
    }

    public void setItemDetailId(Long itemDetailId) {
        this.itemDetailId = itemDetailId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Long fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Long getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Long rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Long getAvailableQuan() {
        return availableQuan;
    }

    public void setAvailableQuan(Long availableQuan) {
        this.availableQuan = availableQuan;
    }
}
