package jpabook.jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpabook.jpashop.domain.item.Item;

@Entity
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;
    private String isbn;


}
