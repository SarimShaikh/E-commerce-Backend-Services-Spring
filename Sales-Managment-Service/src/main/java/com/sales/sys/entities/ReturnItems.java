package com.sales.sys.entities;

import com.sales.sys.entities.audit.EntityBase;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "return_items")
@EntityListeners(AuditingEntityListener.class)
public class ReturnItems extends EntityBase<String> implements Serializable {

    private Long returnId;
    private Long itemId;
    private Long itemDetailId;
    private Long userId;
    private String orderNumber;
    private Date fromDate;
    private Date toDate;
    private Long penaltyAmount;
    private Long quantity;
    private Byte isActive;
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RETURN_ID", nullable = false)
    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
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
    @Column(name = "USER_ID", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "ORDER_NO", nullable = false)
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Basic
    @Column(name = "FROM_DATE", nullable = false)
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Basic
    @Column(name = "TO_DATE", nullable = false)
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Basic
    @Column(name = "PENALTY_AMOUNT", nullable = false)
    public Long getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(Long penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    @Basic
    @Column(name = "QUANTITY", nullable = false)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = false)
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = "complete";
    }
}
