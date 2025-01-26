package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.product.Product;

import java.util.List;

public class Order {
    /**
     * orderid - 주문 번호
     * itemId - 상품 번호
     * name - 이름
     * price - 가격
     * amount - 주문 개수
     * totalprice - 총 가격
     * state - 주문상태
     */

    private Long id;

    private List<Product> orderedProducts;
    private Integer totalPrice;
    private String state;


    public Order(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
        this.totalPrice = calculationTotalPrice(orderedProducts);
        this.state = "CREATED";
    }

    private Integer calculationTotalPrice(List<Product> orderedProducts) {
        return orderedProducts
                .stream()
                .mapToInt(orderedProduct -> orderedProduct.getPrice() * orderedProduct.getAmount())
                .sum();
    }

    public Long getId() {
        return id;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setId(Long id) {
        this.id = id;
    }




}


