package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV1",urlPatterns="/front-controller/v1/*")
//v1 하위에 존재하는 파일에 대해서는  무조건 이 컨트롤러가 호추리된다.
public class FrontControllerServletV1 extends HttpServlet{
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1(){
        controllerMap.put("/front-controller/v1/members/new-form",new MemberFormControllerV1() );
        controllerMap.put("/front-controller/v1/members/save",new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members",new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV1 controllerV1 = controllerMap.get(requestURI);

        if(controllerV1==null){
            response.setStatus(response.SC_NOT_FOUND);
            return;
        }
        //성공적으로 불러 왔으면 process호출->override된 서블릿 객체 실행
        controllerV1.process(request,response);

    }
}
