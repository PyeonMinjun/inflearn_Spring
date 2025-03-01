package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisItemRepository implements ItemRepository {

    private final ItemMapper itemMapper;

    @Override
    public Item save(Item item) {
        itemMapper.save(item);
        return item;
    }

    @Override
    public void update(Long id, ItemUpdateDto updateParam) {
        itemMapper.update(id,updateParam);
    }

    @Override
    public List<Item> findAll(ItemSearchCond itemSearchCond) {
        return itemMapper.findAll(itemSearchCond);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemMapper.findById(id);

    }
}
