package hello.servlet.web.springmvc.old;

import hello.servlet.web.frontcontroller.ModelView;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component("/springmvc/old-controller")//스프링빈의 이름을 url호출과 맞추면 컨트롤러 호출
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(" = ok"  );
        return new ModelAndView("new-form");
    }
}
