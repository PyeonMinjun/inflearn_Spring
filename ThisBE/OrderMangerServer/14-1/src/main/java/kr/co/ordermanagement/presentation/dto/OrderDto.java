package kr.co.ordermanagement.presentation.dto;


import org.springframework.web.bind.annotation.GetMapping;


public class OrderDto {
    private Integer id;
    private int amount;

    public Integer getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

}
