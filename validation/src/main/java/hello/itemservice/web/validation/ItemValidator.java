package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);//파라매터로 넘어온 클래스가 아이템을 지원하는가를 묻는 문제(타입이 같은지를 묻는 문제)

        //Item==clazz
        //Item==subItem(자식)
    }


    //errors에는 rejectValue가 있음
    @Override
    public void validate(Object target, Errors errors) {
        Item item= (Item)target;




        //== 위 아래는 같은 검증 코드이다.

        if(!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required");
        }
        if(item.getPrice()==null ||item.getPrice()<1000 ||item.getPrice()>1000000){
            errors.rejectValue("price","range",new Object[]{1000,1000000},null);
        }
        if(item.getQuantity()==null|| item.getQuantity()>=9999){
            errors.rejectValue("quantity","max",new Object[]{9999},null);
        }

        //특정 필드가 아닌 복합 룰 검증

        if(item.getPrice()!=null && item.getQuantity()!=null){
            int totalPrice=item.getPrice()*item.getQuantity();
            if(totalPrice<10000){
                //글로벌 오류는 필드값이 존재하지 않기 때문에 ObjectError 객체를 생성하여 관리한다.

                errors.reject("totalPriceMin",new Object[]{10000,totalPrice},null);
                //item은 modelAttribute에 담기는 이름
            }
        }

    }
}
