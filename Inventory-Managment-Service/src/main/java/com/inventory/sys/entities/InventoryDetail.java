package com.inventory.sys.entities;

import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "inventory_detail")
@EntityListeners(AuditingEntityListener.class)
public class InventoryDetail extends EntityBase<String> implements Serializable {

    private Long inventoryId;
    private Long itemId;
    private Long itemDetailId;
    private Long availQuantity;
    private Byte isActive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVENTORY_ID", nullable = false)
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
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
    @Column(name = "ITEM_DETAIL_ID", nullable = false)
    public Long getItemDetailId() {
        return itemDetailId;
    }

    public void setItemDetailId(Long itemDetailId) {
        this.itemDetailId = itemDetailId;
    }

    @Basic
    @Column(name = "AVAILABLE_QUANTITY", nullable = false)
    public Long getAvailQuantity() {
        return availQuantity;
    }

    public void setAvailQuantity(Long availQuantity) {
        this.availQuantity = availQuantity;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = false)
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }
}
