package com.inventory.sys.entities;

import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class Category extends EntityBase<String> implements Serializable {

    private Long categoryId;
    private String categoryType;
    private Collection<SubCategory> subCategories;
    private Byte isActive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID", nullable = false)
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "CATEGORY_TYPE", nullable = false)
    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = false)
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive =  isActive;
    }

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    public Collection<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Collection<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
