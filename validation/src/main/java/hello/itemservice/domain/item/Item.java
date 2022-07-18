package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;  //하이버네이트 자체 제공
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;   //BeanValidation에서 기본으로 제공, 모든 구현체에서 같은 구현체를 사용하게 된다.
import javax.validation.constraints.NotNull;

@Data
//direction one
//권징x//@ScriptAssert(lang="javascript" ,script="_this.quantity * _this.price>=10000",message ="총합이 10000원 넘게 입력해주세요")//provided by hibernate  objectError
public class Item {

    @NotNull(groups=UpdateCheck.class)//수정 요구사항 추가, 수정시에는 id가 필수적으로 요구된다.->등록시에도 요구 사항이 반영되어 다른 조건을 사용할 수 없다.
    private Long id;

    @NotBlank(message="null x",groups={UpdateCheck.class,SaveCheck.class})
    private String itemName;
    @NotNull(groups={SaveCheck.class,UpdateCheck.class})
    @Range(min=1000,max=1000000)
    private Integer price;
    @NotNull(groups={SaveCheck.class,UpdateCheck.class})
    @Max(value=9999, groups={SaveCheck.class})
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
