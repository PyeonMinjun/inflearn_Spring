package hello.itemservice.domain;

import lombok.Data;

@Data
public class Item {

    private Long itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer Quantity;


    private Item() {
    }

    public Item(String itemName, Integer itemPrice, Integer Quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.Quantity = Quantity;
    }

}
