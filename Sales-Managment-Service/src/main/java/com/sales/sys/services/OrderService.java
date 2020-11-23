package com.sales.sys.services;

import com.sales.sys.repositories.OrderDetailsRepository;
import com.sales.sys.repositories.OrderRepository;
import com.sales.sys.repositories.RentalItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private RentalItemsRepository rentalItemsRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, RentalItemsRepository rentalItemsRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.rentalItemsRepository = rentalItemsRepository;
    }
}
