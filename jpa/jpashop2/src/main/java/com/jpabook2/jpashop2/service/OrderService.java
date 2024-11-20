package com.jpabook2.jpashop2.service;

import com.jpabook2.jpashop2.domain.*;
import com.jpabook2.jpashop2.domain.item.Item;
import com.jpabook2.jpashop2.repository.ItemRepository;
import com.jpabook2.jpashop2.repository.MemberRepository;
import com.jpabook2.jpashop2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 1.엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 2. 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
//        deliveryRepository.save(delivery);  cascade가 아니였다면 persist 했어야함

        // 3.주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(),count);
//        orderItemRepository.save(orderItem); cascade2

        // 4.주문 생성
        Order order = Order.createOrder(member,delivery,orderItem);

        // 5. 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }

}
