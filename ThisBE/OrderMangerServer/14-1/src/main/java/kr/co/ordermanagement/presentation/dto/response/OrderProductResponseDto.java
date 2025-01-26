package kr.co.ordermanagement.presentation.dto.response;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.presentation.dto.ProductDto;

import java.util.List;

public class OrderProductResponseDto {
    private Long id;
    private List<ProductDto> productDtoLists;
    private Integer totalPrice;
    private String state;

    public OrderProductResponseDto(Long id, List<ProductDto> productDtoLists, Integer totalPrice, String state) {
        this.id = id;
        this.productDtoLists = productDtoLists;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    public static OrderProductResponseDto toDto(Order order) {
        List<ProductDto> orderedProductDtos = order.getOrderedProducts()
                .stream()
                .map(orderedProduct -> ProductDto.toDto(orderedProduct))
                .toList();

        OrderProductResponseDto orderProductResponseDto = new OrderProductResponseDto(
                order.getId(),
                orderedProductDtos,
                order.getTotalPrice(),
                order.getState()
        );
        return orderProductResponseDto;
    }

    public Long getId() {
        return id;
    }

    public List<ProductDto> getProductDtoLists() {
        return productDtoLists;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public String getState() {
        return state;
    }
}
