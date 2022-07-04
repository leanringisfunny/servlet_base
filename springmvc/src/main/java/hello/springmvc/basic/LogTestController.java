package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// @Controller  논리적이름을 반환
@RestController//http massage body에 스트링이 그대로 반환
@Slf4j
public class LogTestController {
    //org.slf4j 패키지의 Logger

    //private final Logger log= LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name= "Spring";

        log.trace("trace log ={}",name);
        log.debug("debug log ={}",name);
        log.info("info log ={}",name);
        log.warn("warn log ={}",name);
        log.error("error log ={}",name);

        return "ok";
    }
}
