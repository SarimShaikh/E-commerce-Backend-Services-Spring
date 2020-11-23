package com.sales.sys.messageDTO;

import java.io.Serializable;
import java.util.Collection;

public class OrderDTO implements Serializable {

    private Long userId;
    private Collection<OrderDetailDTO> orderDetailDTO;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Collection<OrderDetailDTO> getOrderDetailDTO() {
        return orderDetailDTO;
    }

    public void setOrderDetailDTO(Collection<OrderDetailDTO> orderDetailDTO) {
        this.orderDetailDTO = orderDetailDTO;
    }
}
