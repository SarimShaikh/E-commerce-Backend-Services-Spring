package com.sales.sys.repositories;

import com.sales.sys.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);

    Page<Order> getAllByOrderNumber(String orderNum, Pageable pageable);

    Page<Order> getAllByStoreIdOrUserIdOrderByOrderIdDesc(Long storeId , Long userId, Pageable pageable);

    @Override
    Page<Order> findAll(Pageable pageable);
}
