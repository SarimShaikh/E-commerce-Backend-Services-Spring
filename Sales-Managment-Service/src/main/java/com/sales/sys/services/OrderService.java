package com.sales.sys.services;

import com.sales.sys.entities.InventoryDetail;
import com.sales.sys.entities.Order;
import com.sales.sys.entities.OrderDetails;
import com.sales.sys.entities.RentalItems;
import com.sales.sys.exceptions.OrderItemException;
import com.sales.sys.messageDTO.CustomResponseDto;
import com.sales.sys.messageDTO.OrderDTO;
import com.sales.sys.messageDTO.OrderDetailDTO;
import com.sales.sys.repositories.InventoryDetailRepository;
import com.sales.sys.repositories.OrderDetailsRepository;
import com.sales.sys.repositories.OrderRepository;
import com.sales.sys.repositories.RentalItemsRepository;
import com.sales.sys.services.apicall.RestPaymentApi;
import com.sales.sys.utils.UtilsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private RentalItemsRepository rentalItemsRepository;
    private InventoryDetailRepository inventoryDetailRepository;
    private RestPaymentApi restPaymentApi;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, RentalItemsRepository rentalItemsRepository, InventoryDetailRepository inventoryDetailRepository, RestPaymentApi restPaymentApi) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.rentalItemsRepository = rentalItemsRepository;
        this.inventoryDetailRepository = inventoryDetailRepository;
        this.restPaymentApi = restPaymentApi;
    }

    @Transactional
    public CustomResponseDto placeOrder(OrderDTO orderDTO) throws Exception {
        CustomResponseDto customResponseDto = new CustomResponseDto();
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setStoreId(orderDTO.getStoreId());
        order.setOrderNumber(checkOrderNumber(UtilsClass.genRandomOrderNum()));
        order.setCreatedDate(new Date());
        final Order order1 = orderRepository.save(order);
        orderDTO.getPaymentDTO().setOrderNumber(order1.getOrderNumber());
        orderDTO.getPaymentDTO().setDateTime(new Date());
        boolean isPaymentInititate = restPaymentApi.initiatePayment(orderDTO.getPaymentDTO());

        if (isPaymentInititate) {
            for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailDTO()) {
                if (updateInventoryQuantity(orderDetailDTO.getItemDetailId(), orderDetailDTO.getQuantity())) {
                    RentalItems rentalItems = new RentalItems();
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setOrderId(order1.getOrderId());
                    orderDetails.setItemId(orderDetailDTO.getItemId());
                    orderDetails.setItemDetailId(orderDetailDTO.getItemDetailId());
                    orderDetails.setQuantity(orderDetailDTO.getQuantity());
                    orderDetails.setPrice(orderDetailDTO.getPrice());
                    orderDetailsRepository.save(orderDetails);
                    orderDetailsRepository.flush();
                    rentalItems.setItemId(orderDetails.getItemId());
                    rentalItems.setItemDetailId(orderDetails.getItemDetailId());
                    rentalItems.setUserId(orderDTO.getUserId());
                    rentalItems.setStoreId(orderDTO.getStoreId());
                    rentalItems.setOrderNumber(order1.getOrderNumber());
                    rentalItems.setFromDate(UtilsClass.convertDate(orderDetailDTO.getFromDate()));
                    rentalItems.setToDate(UtilsClass.convertDate(orderDetailDTO.getToDate()));
                    rentalItems.setPenaltyAmount((long) 0);
                    rentalItems.setQuantity(orderDetailDTO.getQuantity());
                    rentalItems.setIsActive((byte) 1);
                    rentalItems.setStatus("Active");
                    rentalItemsRepository.save(rentalItems);
                    rentalItemsRepository.flush();
                } else {
                    customResponseDto.setResponseCode("400");
                    customResponseDto.setMessage("Quantity out of stock");
                    throw new OrderItemException(customResponseDto);
                }
            }
        }
        customResponseDto.setResponseCode("200");
        customResponseDto.setMessage("Order Created Successfully!");
        customResponseDto.setOrderNo(order1.getOrderNumber());
        return customResponseDto;
    }

    public Map<String, Object> getAllOrders(String orderNum, int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Order> pageItem = orderNum != null ? orderRepository.getAllByOrderNumber(orderNum, paging) : orderRepository.findAll(paging);
        List<Order> orders = pageItem.getContent();
        response.put("orders", orders);
        response.put("currentPage", pageItem.getNumber());
        response.put("totalItems", pageItem.getTotalElements());
        response.put("totalPages", pageItem.getTotalPages());
        return response;
    }

    public Map<String, Object> getAllByStoreOrUserId(Long storeId, Long userId, int page, int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Order> pageItem = orderRepository.getAllByStoreIdOrUserIdOrderByOrderIdDesc(storeId,userId,paging);
        List<Order> orders = pageItem.getContent();
        response.put("orders", orders);
        response.put("currentPage", pageItem.getNumber());
        response.put("totalItems", pageItem.getTotalElements());
        response.put("totalPages", pageItem.getTotalPages());
        return response;
    }

    private String checkOrderNumber(String orderNum) {
        Order order = orderRepository.findByOrderNumber(orderNum);
        String orderNumber = "";
        if (order != null) {
            orderNumber = order.getOrderNumber().equals(orderNum) ? UtilsClass.genRandomOrderNum() : orderNum;
        } else {
            orderNumber = orderNum;
        }
        return orderNumber;
    }

    private boolean updateInventoryQuantity(Long itemDetailId, Long quantity) {
        boolean validQuantity = false;
        Long updatedQuantity;
        InventoryDetail inventoryDetail = inventoryDetailRepository.findByItemDetailId(itemDetailId);
        if (inventoryDetail.getAvailQuantity() >= quantity) {
            updatedQuantity = inventoryDetail.getAvailQuantity() - quantity;
            inventoryDetail.setAvailQuantity(updatedQuantity);
            inventoryDetailRepository.save(inventoryDetail);
            validQuantity = true;
        }
        return validQuantity;
    }
}
