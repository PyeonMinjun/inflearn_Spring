package com.jpabook2.jpashop2.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter @Setter
public class OrderSearch {

    private String MemberName; // 회원이름
    private OrderStatus orderStatus; // 주문 상태
}
