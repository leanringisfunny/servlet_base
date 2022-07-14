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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }



    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }
//BindingResult는 item객체의 결과를 담고있기 떄문에 item 바로 뒤에 parameter로 명시해야 한다.

    //bindingResult의 의미 :바인딩이 정상적으로 이루어지지 않았으므로 그 결과를 명시해주는데 의의가 있다.
//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //스프링에서 에러를 관리할 수 있는 매커니즘인 BindingResult 제공

        //필드 단위의 에러를 처리할 수 이도록 객체를 스프링에서 제공
        //검증 로직 :글자가 없다면 브라우저에 에러메시지 뿌려주기위해 검증 메시지 저장
        if(!StringUtils.hasText(item.getItemName())){

            bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수 입니다."));
        };
        if(item.getPrice()==null ||item.getPrice()<1000 ||item.getPrice()>1000000){

            bindingResult.addError(new FieldError("item","price","가격은 1000원에서 1000000원 까지 허용합니다"));
        }
        if(item.getQuantity()==null|| item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity","주문량은 9999개를 넘을수 없습니다."));

        }

        //특정 필드가 아닌 복합 룰 검증

        if(item.getPrice()!=null && item.getQuantity()!=null){
            int totalPrice=item.getPrice()*item.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.
                bindingResult.addError(new ObjectError("item","총 주문액은 1만원 이상이어야 함시다.,현재 값"+totalPrice));
                    //item은 modelAttribute에 담기는 이름
            }
        }

        //검증 입력 실패시 다시 입력 폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            //bindingResult는 자동으로 뷰로 함꼐 넘어감
            return "validation/v2/addForm";
        }
        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //스프링에서 에러를 관리할 수 있는 매커니즘인 BindingResult 제공

        //필드 단위의 에러를 처리할 수 이도록 객체를 스프링에서 제공
        //검증 로직 :글자가 없다면 브라우저에 에러메시지 뿌려주기위해 검증 메시지 저장
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품 이름은 필수 입니다."));
        };
        if(item.getPrice()==null ||item.getPrice()<1000 ||item.getPrice()>1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1000원에서 1000000원 까지 허용합니다"));
        }
        if(item.getQuantity()==null|| item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,null,null,"주문량은 9999개를 넘을수 없습니다."));
        }

        //특정 필드가 아닌 복합 룰 검증

        if(item.getPrice()!=null && item.getQuantity()!=null){
            int totalPrice=item.getPrice()*item.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.
                bindingResult.addError(new ObjectError("item",null,null,"총 주문액은 1만원 이상이어야 함시다.,현재 값"+totalPrice));
                //item은 modelAttribute에 담기는 이름
            }
        }

        //검증 입력 실패시 다시 입력 폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            //bindingResult는 자동으로 뷰로 함꼐 넘어감
            return "validation/v2/addForm";
        }
        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        //item with @Data -->toString ㅡㅇㄹ 오버라이드를 하기 떄문에 log.info() 시 object 내의 멤버를(target) 스트링으로 표현해줌

        log.info("objectName={}",bindingResult.getObjectName());
        log.info("target={}",bindingResult.getTarget());
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName"},null,null));
        };
        if(item.getPrice()==null ||item.getPrice()<1000 ||item.getPrice()>1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
        }
        if(item.getQuantity()==null|| item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[] {"max.item.quantity"},new Object[]{10000},null));
        }

        //특정 필드가 아닌 복합 룰 검증

        if(item.getPrice()!=null && item.getQuantity()!=null){
            int totalPrice=item.getPrice()*item.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,totalPrice},null));
                //item은 modelAttribute에 담기는 이름
            }
        }

        //검증 입력 실패시 다시 입력 폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            //bindingResult는 자동으로 뷰로 함꼐 넘어감
            return "validation/v2/addForm";
        }
        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName={}",bindingResult.getObjectName());
        log.info("target={}",bindingResult.getTarget());

        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName","required");
        //== 위 아래는 같은 검증 코드이다.
       /*
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.rejectValue("itemName","required");
        };*/
        if(item.getPrice()==null ||item.getPrice()<1000 ||item.getPrice()>1000000){
            bindingResult.rejectValue("price","range",new Object[]{1000,1000000},null);
        }
        if(item.getQuantity()==null|| item.getQuantity()>=9999){
            bindingResult.rejectValue("quantity","max",new Object[]{9999},null);
        }

        //특정 필드가 아닌 복합 룰 검증

        if(item.getPrice()!=null && item.getQuantity()!=null){
            int totalPrice=item.getPrice()*item.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.

                bindingResult.reject("totalPriceMin",new Object[]{10000,totalPrice},null);
                //item은 modelAttribute에 담기는 이름
            }
        }

        //검증 입력 실패시 다시 입력 폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            //bindingResult는 자동으로 뷰로 함꼐 넘어감
            return "validation/v2/addForm";
        }
        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

