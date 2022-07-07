package hello.thymeleafbasic.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {
    @GetMapping("/text-basic")
    public String TextBasic(Model model){
        model.addAttribute("data","hello <b>thyme leaf!<b>");
        return "basic/text-basic";
    }
    @GetMapping("/text-unescaped")
    public String TextUnescaped(Model model){
        model.addAttribute("data","hello <b>thyme leaf!<b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String Variables(Model model){
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String ,User> map=new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user",userA);
        model.addAttribute("users",list);
        model.addAttribute("userMap",map);

        return "basic/variable";
    }

    @GetMapping("/basic-objects")
    public String BasicObjects(HttpSession session){
        session.setAttribute("sessionData","helloData!");
        return "basic/basic-objects";
    }

    //컴포넌트 스캔으로 스프링 빈으로 등록된다.
    @Component("helloBean")
    static class HelloBean{
        public String hello(String data){
            return "hello"+data;
        }
    }
    @GetMapping("/date")
    public String Date(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }
    @GetMapping("/link")
    public String link(Model model){
        model.addAttribute("param1","data1");
        model.addAttribute("param2","data2");
        return "basic/link";
    }
    @Data //getter setter
    public class User{
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }

}
