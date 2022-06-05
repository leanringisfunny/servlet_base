package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 1.파라매터 전송기능
 * localhost:8080/request-param?username=hello&age=20
 *
 * */

@WebServlet(name="paramRquestServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //모든 파라매터 조회하기 단수 복수 잘 구별해야 이해 쉽게 가능

        System.out.println(" 전체 파라매터 조회[start]");
        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.asIterator().//키 값 하나에 value 하나 가져오기 떄문에 키값이 복수개 여도 가장 앞의 하나 가져옴
                forEachRemaining(parameterName->
                        System.out.println(parameterName + " = "  + request.getParameter(parameterName)));//키:value
        System.out.println(" 전체 파라매터 조회[end]");
        System.out.println( );

        System.out.println(" 단일 파라매터 조회");
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println( );

        System.out.println( "이름이 같은 복수 파라매터 조회");
        String[] usernames = request.getParameterValues("username");
        for (String userName : usernames) {
            System.out.println("userName = " + userName);
        }
        response.getWriter().write("ok");


        }

}

