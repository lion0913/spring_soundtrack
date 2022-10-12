package com.lion.spring_soundtrack.app.cart.service;

import com.lion.spring_soundtrack.app.cart.entity.CartItem;
import com.lion.spring_soundtrack.app.cart.repository.CartItemRepository;
import com.lion.spring_soundtrack.app.member.entity.Member;
import com.lion.spring_soundtrack.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartItemRepository cartItemRepository;

    public CartItem addItem(Member buyer, Product product) {
        CartItem oldCartItem = cartItemRepository.findByBuyerIdAndProductId(buyer.getId(), product.getId());

        if(oldCartItem != null) {
            return oldCartItem;
        }

        CartItem cartItem = CartItem
                .builder()
                .buyer(buyer)
                .product(product)
                .build();

        cartItemRepository.save(cartItem);

        return cartItem;
    }


    public boolean removeItem(Member buyer, Product product) {
        CartItem oldCartItem = cartItemRepository.findByBuyerIdAndProductId(buyer.getId(), product.getId());

        if(oldCartItem != null) {
            cartItemRepository.delete(oldCartItem);
            return true;
        }

        return false;
    }

    public boolean hasItem(Member buyer, Product product) {
        return cartItemRepository.existsByBuyerIdAndProductId(buyer.getId(), product.getId());
    }

    public List<CartItem> getItemsByBuyer(Member buyer) {
        return cartItemRepository.findAllByBuyerId(buyer.getId());
    }

    public void removeItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void removeItem(
            Member buyer,
            Long productId
    ) {
        Product product = new Product(productId);
        removeItem(buyer, product);
    }
}
