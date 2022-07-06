package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository=new ItemRepository();
    @AfterEach
    void clear(){
        itemRepository.clearStore();
    }

    @Test
    void store(){
        Item item = new Item("itemA", 1000, 10);
        Item item1 = itemRepository.save(item);
        Item findItem = itemRepository.findById(item.getId());

        assertThat(item1).isEqualTo(findItem);

    }
    @Test
    void FindAll(){
        Item itemA = itemRepository.save(new Item("itemA", 1000, 10));
        Item itemB = itemRepository.save(new Item("itemB", 2000, 10000));

        List<Item> items = itemRepository.findAll();
        assertThat(2).isEqualTo(items.size());

        assertThat(items).contains(itemA,itemB);

    }
   @Test
   void update(){
        //given
        Item itemA1 = itemRepository.save(new Item("itemA", 1000, 10));
        Item itemA2 = new Item("ItemB", 1500, 15);
        Long id = itemA1.getId();
        //when
        itemRepository.update(id,itemA2);
        //then
        assertThat(itemA2.getPrice()).isEqualTo(itemA1.getPrice());
        assertThat(itemA2.getItemName()).isEqualTo(itemA1.getItemName());
       assertThat(itemA2.getQuantity()).isEqualTo(itemA1.getQuantity());
   }
}