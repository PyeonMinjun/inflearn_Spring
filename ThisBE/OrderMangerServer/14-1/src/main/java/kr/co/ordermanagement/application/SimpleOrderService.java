package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.reqeust.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.response.OrderProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleOrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public SimpleOrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * 1. OrderProductRequestDto의 상품 번호(id)에 해당하는 상품이 주문 수량만큼 재고가 있는지 확인
     * 2. 재고가 있다면 해당 상품의 상품 재고를 차감
     * 3. 차감된 상품 정보를 바탕으로 주문(Order)을 생성
     * 4. 생성된 주문을 OrderResponseDto로 변환하여 반환
     */
    public OrderProductResponseDto createOrder(List<OrderProductRequestDto> orderProductRequestDtos) {

        List<Product> orderedProducts = makeOrderedProducts(orderProductRequestDtos);
        decreaseProductsAmount(orderedProducts);

        Order order = new Order(orderedProducts);
        orderRepository.add(order);

        OrderProductResponseDto orderProductResponseDto = OrderProductResponseDto.toDto(order);
        return orderProductResponseDto;

    }


    private List<Product> makeOrderedProducts(List<OrderProductRequestDto> orderProductRequestDtos) {
        return orderProductRequestDtos
                .stream()
                .map(orderProductRequestDto -> {
                    Long productId = orderProductRequestDto.getId();
                    Product product = productRepository.findById(productId);

                    Integer orderedAmount = orderProductRequestDto.getAmount();
                    product.checkEnoughAmount(orderedAmount);

                    return new Product(
                            productId,
                            product.getName(),
                            product.getPrice(),
                            orderProductRequestDto.getAmount()

                    );
                }).toList();
    }


    private void decreaseProductsAmount(List<Product> orderedProducts) {

        orderedProducts
                .stream()
                .forEach(orderedProduct -> {
                    Long productId = orderedProduct.getId();
                    Product product = productRepository.findById(productId);

                    Integer orderedAmount = orderedProduct.getAmount();
                    product.decreaseAmount(orderedAmount);

                    productRepository.update(product);

                });
    }


}
