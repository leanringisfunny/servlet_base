package hello.servlet.web.frontcontroller.V5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.V4.controller.ControllerV4;
import hello.servlet.web.frontcontroller.V5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public Boolean supports(Object Handler) {
        return (Handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;
        Map<String,String> paramMap=new HashMap<>();
        Map<String ,Object>model=new HashMap<>();
        String viewName = controller.process(paramMap, model);

        ModelView modelView = new ModelView(viewName);
        //기존 컨트롤러에서는 string을 반환했었으나 adapter의 역할을 수행하기 위해서(다형성을 통한 일치) modelview를 직접 반환하도록 한다.
        modelView.setModel(model);//모델뷰에 생성자로 논리이름을 세팅함과 동시에 model을 세팅한다.

        return mv;
    }
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,
                        request.getParameter(paramName)));
        return paramMap;
    }
}
