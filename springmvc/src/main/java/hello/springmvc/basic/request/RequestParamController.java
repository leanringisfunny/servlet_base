package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j//logging
@Controller
public class RequestParamController {


    @RequestMapping("/request-param-v1")
    public void RequestParamV1(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username = request.getParameter("username");
        int age= Integer.parseInt(request.getParameter("age"));

        log.info("username={} age={}",username,age);
        response.getWriter().write("ok");//IOExcepcion이 생길 수 있으므로 예외처리 IMPORT가 필요하다.
        //리턴 값이 VOID값이면서 response값을 write를 하면 쓴 값을 전달
    }

    //form action경로를 request-param-v1으로 설정하면 폼의 파라매터 값도 이 컨트롤러로 받을 수 있으며, 쿼리로 직접 전달해 받을 수 도 있다
    //controller이면서 return 값이 String이라면 논리적(뷰 네임)을 반환한다.
    //RestController로 바꿈으로써 리턴 값을 response에 콱! 다이렉트로 넣을 수 있음
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String RequestParamV2(@RequestParam("username")String MemberName,
                               @RequestParam("age") int MemberAge)
    {
        log.info("username={} age={}",MemberName,MemberAge);
        return "ok";
    }
    //@RequestParam 괄호 내의 키값 생략 가능 하지만, 변수명과 파라매터의 키값이 변수명과 같아야한다.
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String RequestParamV3(@RequestParam String username,
                                 @RequestParam int age)
    {
        log.info("username={} age={}",username,age);
        return "ok";
    }

    //아예 @RequestParam을 import조차 하지 않고 파라매터로 받을 수 있으나, 역시 변수명이 키값과 맞아야 한다.
    //(String ,Integer, int등의 단순 타입이라면,)
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String RequestParamV4(String username,
                                  int age)
    {
        log.info("username={} age={}",username,age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String RequestParamRequeted(@RequestParam(required=true) String username,
                                       @RequestParam(required=false)  Integer age)
    {
        log.info("username={} age={}",username,age);
        return "ok";
    }

    //request param 아무 입력 없을 시 default 값 설정하기 문자열로 받으면 공백 문자도 디폴트 값으로 처리해준다.
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String RequestParamDefault(@RequestParam(required=true, defaultValue = "guest") String username,
                                       @RequestParam(required=false)  Integer age)
    {
        log.info("username={} age={}",username,age);
        return "ok";
    }

    //모든 파라매터 정보를 또한,키 밸루 쌍으로서 맵으로 받을 수 있다
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String RequestParamMap(@RequestParam Map<String,Object>paramMap )
    {
        log.info("username={} age={}",paramMap.get("username"),paramMap.get("age"));
        return "ok";
    }
}
