package com.lion.spring_soundtrack.app.cash.entity;


import com.lion.spring_soundtrack.app.base.entity.BaseEntity;
import com.lion.spring_soundtrack.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class CashLog extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private long price;

    private String eventType;
}
