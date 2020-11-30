package com.sales.sys.controller;

import com.sales.sys.messageDTO.CustomResponseDto;
import com.sales.sys.messageDTO.OrderDTO;
import com.sales.sys.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/sales")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CustomResponseDto placeOrder(@RequestBody OrderDTO orderDTO)throws Exception{
        return orderService.placeOrder(orderDTO);
    }

}
