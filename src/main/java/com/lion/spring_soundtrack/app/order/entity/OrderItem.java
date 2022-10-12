package com.lion.spring_soundtrack.app.order.entity;

import com.lion.spring_soundtrack.app.base.entity.BaseEntity;
import com.lion.spring_soundtrack.app.product.entity.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@SuperBuilder
public class OrderItem extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;

    private LocalDateTime payDate;

    @ManyToOne(fetch = LAZY)
    private Product product;

    // 가격
    private int price; // 권장판매가
    private int salePrice; // 실제판매가
    private int wholesalePrice; // 도매가
    private int pgFee; // 결제대행사 수수료
    private int payPrice; // 결제금액
    private int refundPrice; // 환불금액
    private boolean isPaid; // 결제여부

    public OrderItem(Product product) {
        this.product = product;
        this.price = product.getPrice();
        this.salePrice = product.getSalePrice();
        this.wholesalePrice = product.getWholesalePrice();
    }

    public void setPaymentDone() {
        this.pgFee = 0;
        this.payPrice = getSalePrice();
        this.isPaid = true;
        this.payDate = LocalDateTime.now();
    }

    public void setRefundDone() {
        this.refundPrice = payPrice;
    }
}
