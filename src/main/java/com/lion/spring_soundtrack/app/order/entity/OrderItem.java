package com.lion.spring_soundtrack.app.order.entity;

import com.lion.spring_soundtrack.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Data
@SuperBuilder
public class OrderItem extends BaseEntity {

}
