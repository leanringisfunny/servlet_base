package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }

    @PostMapping("/add")
    //item으로 필드명을 지정해줘야 뷰템플릿을 바꾸지않고 제대로 동작할 수 있음
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //direction 2
        if(form.getPrice()!=null && form.getQuantity()!=null){
            int totalPrice=form.getPrice()*form.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.

                bindingResult.reject("totalPriceMin",new Object[]{10000,totalPrice},null);
                //item은 modelAttribute에 담기는 이름
            }
        }

        //검증 입력 실패시 다시 입력 폼으로 이동(에러 생기면)
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            //bindingResult는 자동으로 뷰로 함꼐 넘어감
            return "validation/v4/addForm";
        }
        //성공 로직
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());

        Item savedItem = itemRepository.save(item);//id는 레포지토리에서 생성
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute("item")  @Validated ItemUpdateForm form, BindingResult bindingResult) {

        if(form.getPrice()!=null && form.getQuantity()!=null){
            int totalPrice=form.getPrice()*form.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.
                bindingResult.reject("totalPriceMin",new Object[]{10000,totalPrice},null);
                //item은 modelAttribute에 담기는 이름
            }
        }
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "validation/v4/editForm";
        }

        Item itemParam = new Item();
        itemParam.setItemName(form.getItemName());
        itemParam.setPrice(form.getPrice());
        itemParam.setQuantity(form.getQuantity());
        itemRepository.update(itemId, itemParam);
        return "redirect:/validation/v4/items/{itemId}";
    }
}

