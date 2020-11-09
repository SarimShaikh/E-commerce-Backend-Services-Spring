package com.inventory.sys.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "images")
@EntityListeners(AuditingEntityListener.class)
public class Images extends EntityBase<String> implements Serializable {

    private Long imageId;
    private String imagePath;
    private Long itemId;
    private Item item;
    private String encodeImage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID", nullable = false)
    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "IMAGE_PATH", nullable = false)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Basic
    @Column(name = "ITEM_ID", nullable = false)
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID" ,nullable = false , insertable = false , updatable = false)
    @JsonBackReference
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @org.springframework.data.annotation.Transient
    public String getEncodeImage() {
        return encodeImage;
    }

    public void setEncodeImage(String encodeImage) {
        this.encodeImage = encodeImage;
    }
}
