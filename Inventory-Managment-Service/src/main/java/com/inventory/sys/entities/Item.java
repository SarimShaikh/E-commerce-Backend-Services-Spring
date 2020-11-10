package com.inventory.sys.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
public class Item extends EntityBase<String> implements Serializable {

    private Long itemId;
    private Long companyId;
    private Long categoryId;
    private Long subCategoryId;
    private String itemName;
    private Company company;
    private Category category;
    private SubCategory subCategory;
    Collection<Images> images;
    Collection<ItemDetails> itemDetails;
    private Byte isActive;

    //extra fields to get parent details
    private String companyName;
    private String categoryName;
    //private String subCategoryName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID", nullable = false)
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "COMPANY_ID", nullable = false)
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
    @Column(name = "SUB_CATEGORY_ID", nullable = false)
    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    @Basic
    @Column(name = "ITEM_NAME", nullable = false)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @ManyToOne(optional = false , fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID" ,nullable = false , insertable = false , updatable = false)
    @JsonIgnore
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(optional = false , fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID" ,nullable = false , insertable = false , updatable = false)
    @JsonBackReference
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_CATEGORY_ID" ,nullable = false , insertable = false , updatable = false)
    @JsonBackReference
    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = false)
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    public Collection<Images> getImages() {
        return images;
    }

    public void setImages(Collection<Images> images) {
        this.images = images;
    }

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    public Collection<ItemDetails> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(Collection<ItemDetails> itemDetails) {
        this.itemDetails = itemDetails;
    }

    @org.springframework.data.annotation.Transient
    public String getCompanyName() {
        return companyName;
    }

    @org.springframework.data.annotation.Transient
    public String getCategoryName() {
        return categoryName;
    }

    /*@org.springframework.data.annotation.Transient
    public String getSubCategoryName() {
        return subCategoryName;
    }*/

    //set values in extra fields
    public void setCompanyName(String companyName) {
        this.companyName = company.getCompanyName();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = category.getCategoryType();
    }

    /*public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategory.getSubCategoryType();
    }*/
}
