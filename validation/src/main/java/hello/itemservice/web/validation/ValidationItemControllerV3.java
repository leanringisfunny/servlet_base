package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    @PostMapping("/add")
    //org.springframework.boot:spring-web-starte-validation 추가시,spring이 자동으로 GlobalValidatorFactoryBean(검증기)를 등록해
    // @Validated 애노테이션(검증기 사용을 위해 무조건 필요)으로 자동으로 bean validation(데이터의 애너테이션)이 수행이 된다.
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //direction 2
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int totalPrice=item.getPrice()*item.getQuantity();
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
            return "validation/v3/addForm";
        }
        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute  @Validated Item item, BindingResult bindingResult) {

        if(item.getPrice()!=null && item.getQuantity()!=null){
            int totalPrice=item.getPrice()*item.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.
                bindingResult.reject("totalPriceMin",new Object[]{10000,totalPrice},null);
                //item은 modelAttribute에 담기는 이름
            }
        }
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "validation/v3/editForm";
        }
        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

