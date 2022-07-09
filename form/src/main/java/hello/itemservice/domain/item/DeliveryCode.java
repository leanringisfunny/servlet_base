package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FAST:빠른 배송
 * NORMAL:일반 배송
 * SLOW: 느린 배송
 */
@Data//GETTER SETTER
@AllArgsConstructor// 생성자 주입까지 도와줌
public class DeliveryCode {
    private String code;        //FAST(시스템에서 전달하느 값)
    private String displayName; //빠른 배송(고객에게 보여주기 위한 값)


}
