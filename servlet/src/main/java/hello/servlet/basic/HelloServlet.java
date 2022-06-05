package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//서블릿이 호출되면 서비스가 호출된다.
@WebServlet(name="helloServlet" ,urlPatterns="/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.service(req, resp);
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // 쿼리 스트링 받기
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        //response option설정
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //http response 메시지바디에 데이터가 들어간다
        response.getWriter().write("hello "+username);
    }
}
