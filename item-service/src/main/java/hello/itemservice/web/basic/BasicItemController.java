package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor//생성자 자동 생성
public class BasicItemController
{
    private final ItemRepository itemRepository;

    /*
    @Autowired //생성자가 하나일 시에는 @AutoWired생략가능
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }*/

    @GetMapping
    public String ShowItems(Model model){
        List<Item> itemList = itemRepository.findAll();
        model.addAttribute("items",itemList);

        return "basic/items";
    }
    @GetMapping("/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String add(){
        return"basic/addForm";
    }
    //requestparam으로 받을 수도 있지만 modelAttribute로 받으면 어뎁터 과정을 거쳐서 객체를 직접 생성해서 매개변수로 넘겨준다.
    @PostMapping("/add")
    public String addItem(Model model ,@ModelAttribute("item")Item item){
        //ModelAttribute안의 ()에는 따로 지정하지 않는다면 클래스명의 첫 대문자를 소문자로 바꾼  item이라는 key를 생성하며 item객체를 값으로 가지도록해 addAttribute과정을 생략해도 된다.
        Item savedItem = itemRepository.save(item);
        //model.addAttribute("item",item);
        return"basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @ModelAttribute Item paramItem){
        itemRepository.update(itemId,paramItem);
        return "redirect:/basic/items/{itemId}";
    }
    /**
     * for test, init initial data
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",100,10000));
        itemRepository.save(new Item("itemB",20,150000));

    }
}
