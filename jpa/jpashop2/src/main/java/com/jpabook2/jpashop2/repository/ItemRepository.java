package com.jpabook2.jpashop2.repository;

import com.jpabook2.jpashop2.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // save
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else em.merge(item);
    }

    // find one
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return  em.createQuery("select i from Item  i", Item.class)
                .getResultList();
    }

}
