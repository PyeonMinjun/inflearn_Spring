package com.jpabook2.jpashop2.controller;


import com.jpabook2.jpashop2.domain.Member;
import com.jpabook2.jpashop2.domain.Order;
import com.jpabook2.jpashop2.domain.OrderSearch;
import com.jpabook2.jpashop2.domain.item.Item;
import com.jpabook2.jpashop2.service.ItemService;
import com.jpabook2.jpashop2.service.MemberService;
import com.jpabook2.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;

    private final ItemService itemService;

    private final OrderService orderService;


    @GetMapping("/order")
    public String orderForm(Model model) {

        List<Member> members = memberService.findMemberAll();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String orderForm(@RequestParam("memberId") Long memberId,
                            @RequestParam("itemId") Long itemId,
                            @RequestParam("number") int number) {
        // 주문하면 order에 저장
        orderService.order(memberId, itemId, number);

        return "redirect:/orders";

    }

    @GetMapping("/orders")
    public String orders(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "/order/orderList";
    }

    @PostMapping("orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }



}
