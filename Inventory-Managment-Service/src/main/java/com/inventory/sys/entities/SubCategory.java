package com.inventory.sys.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sub_category")
@EntityListeners(AuditingEntityListener.class)
public class SubCategory extends EntityBase<String> implements Serializable {

    private Long subCategoryId;
    private Long categoryId;
    private String subCategoryType;
    private Category category;
    private Byte isAvtive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUB_CATEGORY_ID", nullable = false)
    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    @Basic
    @Column(name = "CATEGORY_ID", nullable = false)
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "SUB_CATEGORY_TYPE", nullable = false)
    public String getSubCategoryType() {
        return subCategoryType;
    }

    public void setSubCategoryType(String subCategoryType) {
        this.subCategoryType = subCategoryType;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID" ,nullable = false , insertable = false , updatable = false)
    @JsonBackReference
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = false)
    public Byte getIsAvtive() {
        return isAvtive;
    }

    public void setIsAvtive(Byte isAvtive) {
        this.isAvtive = isAvtive;
    }
}
