package com.lion.spring_soundtrack.app.cart.repository;

import com.lion.spring_soundtrack.app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByBuyerIdAndProductId(Long buyerId, Long productId);

    boolean existsByBuyerIdAndProductId(Long buyerId, Long productId);

    List<CartItem> findAllByBuyerId(long buyerId);
}
