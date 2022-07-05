package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper= new ObjectMapper();
    @PostMapping("request-body-json-v1")
    public void RequestBodyJsonV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={} age={}",helloData.getUsername(),helloData.getAge());
        response.getWriter().write("ok");
    }
    //@RequestBody를 생략하면 ModelAttribute가 호출이 된다
    @ResponseBody
    @PostMapping("request-body-json-v2")
    public String RequestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={} age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }
//object mapper없이 바디의 제이슨 데이터를 바로 매개변수로 받아서 사용할 수 있음
    @ResponseBody
    @PostMapping("request-body-json-v3")
    public String RequestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("username={} age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }
    //제네릭으로 선언이 되어있기 떄문에 겟 바디로 꺼내면 제네릭으로 반환이된다.
    @ResponseBody
    @PostMapping("request-body-json-v4")
    public String RequestBodyJsonV4(HttpEntity<HelloData> data) {
        HelloData helloData = data.getBody();
        log.info("username={} age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }
    //response body에 제이슨 데이터로 만든 객체를 집어넣을 수 있다. 객체는 제이슨으로 표현된다.
    @ResponseBody
    @PostMapping("request-body-json-v5")
    public HelloData RequestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("username={} age={}",helloData.getUsername(),helloData.getAge());
        return helloData;
    }
}
