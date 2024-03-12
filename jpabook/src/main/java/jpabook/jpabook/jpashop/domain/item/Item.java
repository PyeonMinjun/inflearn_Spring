package jpabook.jpabook.jpashop.domain.item;


import jakarta.persistence.*;
import jpabook.jpabook.jpashop.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

//    private List<OrderItem> categories = ArrayList<>();



}
