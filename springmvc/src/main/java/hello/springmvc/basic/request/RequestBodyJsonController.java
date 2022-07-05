package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
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
}
