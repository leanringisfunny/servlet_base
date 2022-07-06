package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class ItemRepository {
    private final static Map<Long,Item> store= new HashMap<>();
    private static Long sequence=0L;
    public Item save(Item item){
        item.setId(++sequence);
        store.put(sequence,item);
        return item;
    }
    public Item findById(Long Id){
        return store.get(Id);
    }
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }
    public void update(Long id, Item param){
        Item item = findById(id);
        item.setItemName(param.getItemName());
        item.setPrice(param.getPrice());
        item.setQuantity(param.getQuantity());
    }
    public void clearStore(){
        store.clear();
    }
}
