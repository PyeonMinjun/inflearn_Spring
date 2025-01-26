package kr.co.ordermanagement.presentation.controller;

import kr.co.ordermanagement.application.SimpleOrderService;
import kr.co.ordermanagement.presentation.dto.reqeust.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.response.OrderProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestController {

    public SimpleOrderService simpleOrderService;

    public OrderRestController(SimpleOrderService simpleOrderService) {
        this.simpleOrderService = simpleOrderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderProductResponseDto> createOrder(
            @RequestBody List<OrderProductRequestDto> orderProductRequestDtos
    ) {
        OrderProductResponseDto orderResponseDto = simpleOrderService.createOrder(orderProductRequestDtos);
        return ResponseEntity.ok(orderResponseDto);
    }
    // 1. entity
    // 2. dto 만들어야하고
    // 3. service
    // 4. 레포 만들고
}
