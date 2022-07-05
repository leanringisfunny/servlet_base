package hello.springmvc.basic.response;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {
    @RequestMapping("/response-view-v1")
    public ModelAndView responoseViewV1(){
        ModelAndView mav = new ModelAndView("response/hello").addObject("data","hello!");
        return mav;
    }
    @RequestMapping("/response-view-v2")
    public String responoseViewV2(Model model){
        model.addAttribute("data","hello!");
        return "response/hello";
    }
    //컨트롤러의 경로와 뷰의 논리적 경로가 같으면 void로 리턴해도 http응답 메시지로 위의 동작과 같은 방식으로 html을 뿌려준다.
    @RequestMapping("/response/hello")
    public void  responoseViewV3(Model model){
        model.addAttribute("data","hello!");
    }
}
