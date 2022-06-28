package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV2",urlPatterns="/front-controller/v2/*")
//v1 하위에 존재하는 파일에 대해서는  무조건 이 컨트롤러가 호추리된다.
public class FrontControllerServletV2 extends HttpServlet{
    Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2(){
        controllerMap.put("/front-controller/v2/members/new-form",new MemberFormControllerV2() );
        controllerMap.put("/front-controller/v2/members/save",new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members",new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV2 controllerV2 = controllerMap.get(requestURI);

        if(controllerV2==null){
            response.setStatus(response.SC_NOT_FOUND);
            return;
        }
        //성공적으로 불러 왔으면 process호출->override된 서블릿 객체 실행
        MyView myView = controllerV2.process(request, response);

        myView.render(request,response);

    }
}
