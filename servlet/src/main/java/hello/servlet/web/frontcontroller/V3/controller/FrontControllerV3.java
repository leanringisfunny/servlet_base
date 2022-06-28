package hello.servlet.web.frontcontroller.V3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
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

@WebServlet(name="frontControllerServletV3",urlPatterns="/front-controller/v3/*")
//v1 하위에 존재하는 파일에 대해서는  무조건 이 컨트롤러가 호추리된다.
public class FrontControllerV3 extends HttpServlet{
    Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerV3(){
        controllerMap.put("/front-controller/v3/members/new-form",new MemberFormControllerV3() );
        controllerMap.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV3 controllerV3 = controllerMap.get(requestURI);

        if(controllerV3==null){
            response.setStatus(response.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);

        //성공적으로 불러 왔으면 process호출->override된 서블릿 객체 실행
        ModelView mv = controllerV3.process(paramMap);

        MyView view = viewResolver(mv);

        view.render(mv.getModel(),request,response);

    }

    private MyView viewResolver(ModelView mv) {
        MyView view = new MyView("/WEB-INF/views/" + mv.getViewName() + ".jsp");
        return view;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName->paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }


}
