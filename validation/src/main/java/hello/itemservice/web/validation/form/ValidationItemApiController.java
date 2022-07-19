package hello.itemservice.web.validation.form;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem (@RequestBody @Validated ItemSaveForm form , BindingResult bindingResult){
        log.info("API컨트롤러 호출");

        if(bindingResult.hasErrors()){
            log.info("errors has occured={}", bindingResult);
            return bindingResult.getAllErrors();
        }
        log.info("성공로직 실행");

        return form;
    }

}
