package com.lion.spring_soundtrack.app.order.repository;

import com.lion.spring_soundtrack.app.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
