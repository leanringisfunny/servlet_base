package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController
{
    @PostMapping("/request-body-string-v1")
    public void requestBodyString (HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        //stream은 바이트 코드이므로 바이트 코드를 입력으로 받을때에는 어떤 uft를 사용할 것인지에대한 정보를 명시해야한다.
        log.info("messageBody={}",messageBody);
        response.getWriter().write("ok");
    }
    //stream을 사용할 필요도 없이 스프링이 처리를 해준다.
    @PostMapping("/request-body-string-v2")
    public void requestBodyInputStream (InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        //stream은 바이트 코드이므로 바이트 코드를 입력으로 받을때에는 어떤 uft를 사용할 것인지에대한 정보를 명시해야한다.
        log.info("messageBody={}",messageBody);
        responseWriter.write("ok");
    }

    //body를 String으로 변환해주는 http message converter를 제공해줘서 편하게 body의 내용을 조회하고 사용할 수 있다
    @PostMapping("/request-body-string-v3")//RequestEntity, ResponseEntity는 HttpEntity를 상속받았으므로 사용가능하나 기능 제공이 아쉬움
    public HttpEntity<String> requestBodyGetBody (RequestEntity<String> httpEntity) throws IOException {
        String body = httpEntity.getBody();
        //stream은 바이트 코드이므로 바이트 코드를 입력으로 받을때에는 어떤 uft를 사용할 것인지에대한 정보를 명시해야한다.
        log.info("messageBody={}",body);
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
        //response body에 원하는 메시지 값을 넣는다.(view 조회 x http body에 정보를 콱 넣어버림)
    }

    @PostMapping("/request-body-string-v4")//RequestBody에 스트림으로 돼있으면 String인 messageBody에 콱 넣어줌
    @ResponseBody//리턴 값을 response body에 콱 넣어줌!
    public String requestBodyGetBodyWithAnnotation (@RequestBody String messageBody) throws IOException {

        //stream은 바이트 코드이므로 바이트 코드를 입력으로 받을때에는 어떤 uft를 사용할 것인지에대한 정보를 명시해야한다.
        log.info("messageBody={}",messageBody);
        return "ok";
        //response body에 원하는 메시지 값을 넣는다.(view 조회 x http body에 정보를 콱 넣어버림)
    }
}

