package hello.itemservice.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static Long instance = 0L;


    public Item save(Item item) {  // 아이디 증가시킴, 그이후 store 추가,
        item.setId(++instance);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());

    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setName(updateParam.getName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
