package com.inventory.sys.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "images")
public class Images implements Serializable {

    private Long imageId;
    private String imagePath;
    private Long itemId;
    private Item item;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "ITEM_ID" ,nullable = false , insertable = false , updatable = false)
    @JsonBackReference
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
