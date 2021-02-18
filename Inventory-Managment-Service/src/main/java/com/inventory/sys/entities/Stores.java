package com.inventory.sys.entities;

import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "stores")
@EntityListeners(AuditingEntityListener.class)
public class Stores extends EntityBase<String> implements Serializable {

    private Long storeId;
    private Long userId;
    private String storeName;
    private String storeRegistrationNumber;
    private String storeContact;
    private String storeAddress;
    private String imagePath;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID", nullable = false)
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Column(name = "USER_ID", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "STORE_NAME", nullable = false)
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Basic
    @Column(name = "STORE_REG_NO", nullable = false)
    public String getStoreRegistrationNumber() {
        return storeRegistrationNumber;
    }

    public void setStoreRegistrationNumber(String storeRegistrationNumber) {
        this.storeRegistrationNumber = storeRegistrationNumber;
    }

    @Basic
    @Column(name = "STORE_CONTACT", nullable = false)
    public String getStoreContact() {
        return storeContact;
    }

    public void setStoreContact(String storeContact) {
        this.storeContact = storeContact;
    }

    @Basic
    @Column(name = "STORE_ADDRESS", nullable = false)
    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    @Basic
    @Column(name = "IMAGE")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
