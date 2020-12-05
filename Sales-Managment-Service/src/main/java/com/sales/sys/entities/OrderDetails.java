package com.sales.sys.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private Long orderDetailId;
    private Long orderId;
    private Long itemId;
    private Long itemDetailId;
    private Long quantity;
    private Long price;
    private Order order;
    private Item item;

    //extra fields to get parent details
    private String itemName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_ID", nullable = false)
    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    @Basic
    @Column(name = "ORDER_ID", nullable = false)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    @Column(name = "QUANTITY", nullable = false)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "PRICE", nullable = false)
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "ITEM_ID", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @org.springframework.data.annotation.Transient
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = item.getItemName();
    }
}
