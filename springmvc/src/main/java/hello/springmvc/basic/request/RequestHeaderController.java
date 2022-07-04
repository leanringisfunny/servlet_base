package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {
    @RequestMapping("/headers")
    public String hewaders(HttpServletRequest request,
                           HttpServletResponse response,
                           HttpMethod httpMethod,
                           Locale locale,
                           @RequestHeader MultiValueMap<String,String> headerMap,
                           @RequestHeader("host")String host,
                           @CookieValue(value="myCookie",required=false)String Cookie
                           ){
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod); //post맨으로 요청 보내면 요청 메서드의 정보를 표시해줌
        log.info("locale={}", locale);  //가장 우선순위가 높은 locale이 지정됨 (지역 우선순위 ko-KR)
        log.info("headerMap={}", headerMap);//요청의 http 헤더 정보가  키밸루 썅으로 모두 들어온다.
        log.info("header host={}", host);// host키를 가진 밸루 값만 들어온다.
        log.info("myCookie={}", Cookie);
        return "ok";
    }
}
