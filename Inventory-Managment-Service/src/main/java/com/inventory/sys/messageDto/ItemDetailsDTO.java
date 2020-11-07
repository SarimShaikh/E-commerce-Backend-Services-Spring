package com.inventory.sys.messageDto;

import java.io.Serializable;

public class ItemDetailsDTO implements Serializable {

    private Long itemDetailId;
    private Long itemId;
    private String itemSize;
    private Long itemPrice;
    private Long fineAmount;
    private Long rentalDays;
    private Byte isActive;

    public Long getItemDetailId() {
        return itemDetailId;
    }

    public void setItemDetailId(Long itemDetailId) {
        this.itemDetailId = itemDetailId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }
}
