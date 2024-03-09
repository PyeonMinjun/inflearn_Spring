package hello.itemservice.domain;

import lombok.Data;

@Data
public class Item {

    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;


    public Item() {
    }

    public Item(String itemName, Integer itemPrice, Integer quantity) {
        this.name = itemName;
        this.price = itemPrice;
        this.quantity = quantity;
    }

}
