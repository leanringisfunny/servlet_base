package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;  //하이버네이트 자체 제공

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;   //BeanValidation에서 기본으로 제공, 모든 구현체에서 같은 구현체를 사용하게 된다.
import javax.validation.constraints.NotNull;

@Data
public class Item {

    private Long id;
    @NotBlank(message="null x")
    private String itemName;
    @NotNull
    @Range(min=1000,max=1000000)
    private Integer price;
    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
