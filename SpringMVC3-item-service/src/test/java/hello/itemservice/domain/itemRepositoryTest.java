package hello.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class itemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void clearStore() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("prod1", 1000, 10);

        //when
        Item saveItems = itemRepository.save(item);

        //then
        assertThat(item).isEqualTo(saveItems);


    }


    @Test
    void findById() {
        //given
        Item item = new Item("prod1", 1000, 10);
        itemRepository.save(item);

        //when
        Item byId = itemRepository.findById(item.getItemId());

        //then
        assertThat(byId.getItemId()).isEqualTo(item.getItemId());
    }

    @Test
    void findAll() {

        //given
        Item item1 = new Item("prod1", 1000, 10);
        Item item2 = new Item("prod2", 2000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> all = itemRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(item1,item2);

    }

    @Test
    void updateItem() {
        //given
        Item item1 = new Item("prod1", 1000, 10);
        Item saveItem = itemRepository.save(item1);

        Long itemId = saveItem.getItemId();

        //when
        Item updateParam = new Item("prod2", 2000, 20);
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);

        //then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getItemPrice()).isEqualTo(updateParam.getItemPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }


}
