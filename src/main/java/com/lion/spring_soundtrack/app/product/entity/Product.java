package com.lion.spring_soundtrack.app.product.entity;

import com.lion.spring_soundtrack.app.base.entity.BaseEntity;
import com.lion.spring_soundtrack.app.member.entity.Member;
import com.lion.spring_soundtrack.app.song.entity.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {
    private String subject;
//    private String content;

    @ManyToOne(fetch = LAZY)
    private Member author;

    @ManyToOne(fetch = LAZY)
    private Song song;

    private int price;

    public Product(long id) {
        super(id);
    }

    public int getSalePrice() {
        return getPrice();
    }

    public int getWholesalePrice() {
        return (int) Math.ceil(getPrice() * 0.7);
    }

    public boolean isOrderable() {
        return true;
    }
}
