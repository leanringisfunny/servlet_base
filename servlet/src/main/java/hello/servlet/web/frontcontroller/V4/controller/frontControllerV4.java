package hello.servlet.web.frontcontroller.V4.controller;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV4",urlPatterns="/front-controller/v4/*")
public class frontControllerV4 extends HttpServlet {
    private Map<String, ControllerV4>controllerMap=new HashMap();

    public frontControllerV4() {
        controllerMap.put("/front-controller/v4/members/new-form",new MemberFormControllerV4() );
        controllerMap.put("/front-controller/v4/members/save",new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        ControllerV4 controllerV4 = controllerMap.get(requestURI);

        if (controllerV4 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String ,Object>model =new HashMap();
        String viewName = controllerV4.process(paramMap, model);

        MyView myView = viewResolver(viewName);

        myView.render(model,request,response);

    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String>paramMap =new HashMap();
        request.getParameterNames().asIterator().forEachRemaining((parameterName)->paramMap.put(parameterName, request.getParameter(parameterName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return view;
    }
}
