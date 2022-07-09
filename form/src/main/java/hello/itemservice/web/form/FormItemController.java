package hello.itemservice.web.form;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    //타임 리프를 쓴다면 빈 아이템을 넘겨줘서 Item 객체를 폼에 넘겨준다.(빈 객체를 넘겨주고 채워주는 방법이 있다.)
    // (기존에는  폼데이터를 파라매터로 받아서 객체로 어뎁터 해주는 기능)
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item",new Item("koko",10,10));//값을 넣면 폼의 value값에 들어감
        return "form/addForm";
    }
    //폼에서 포스트로 정보를 넘기면 Model Attribute로 받아 어뎁터를 통해 객체를 생성해준다.
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

}

