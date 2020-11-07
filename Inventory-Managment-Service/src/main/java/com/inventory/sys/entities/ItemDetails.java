package com.inventory.sys.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "items_detail")
@EntityListeners(AuditingEntityListener.class)
public class ItemDetails extends EntityBase<String> implements Serializable {

    private Long itemDetailId;
    private Long itemId;
    private String itemSize;
    private Long itemPrice;
    private Long fineAmount;
    private Long rentalDays;
    private Byte isActive;
    private Item item;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_DETAIL_ID", nullable = false)
    public Long getItemDetailId() {
        return itemDetailId;
    }

    public void setItemDetailId(Long itemDetailId) {
        this.itemDetailId = itemDetailId;
    }

    @Basic
    @Column(name = "ITEM_ID", nullable = false)
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "ITEM_SIZE", nullable = false)
    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    @Basic
    @Column(name = "ITEM_PRICE", nullable = false)
    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Basic
    @Column(name = "FINE_AMOUNT", nullable = false)
    public Long getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Long fineAmount) {
        this.fineAmount = fineAmount;
    }

    @Basic
    @Column(name = "RENTAL_DAYS", nullable = false)
    public Long getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Long rentalDays) {
        this.rentalDays = rentalDays;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = false)
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID" ,nullable = false , insertable = false , updatable = false)
    @JsonManagedReference
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
