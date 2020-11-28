package com.payment.service.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private Long paymentId;
    private String paymentType;
    private Date dateTime;
    private Integer Amount;
    private String orderNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID", nullable = false)
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @Basic
    @Column(name = "PAYMENT_TYPE", nullable = false)
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TIME")
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Basic
    @Column(name = "AMOUNT", nullable = false)
    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    @Basic
    @Column(name = "ORDER_NO", nullable = false)
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
