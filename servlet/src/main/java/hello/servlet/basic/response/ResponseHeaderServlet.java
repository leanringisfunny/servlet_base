package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //status line
        response.setStatus(HttpServletResponse.SC_OK);

        //response-headers
        response.setHeader("Content-Type","text-plain;charset=UTF-8");
        response.setHeader("Cache-Controll","no-cache,  no-store, must-revalidate");
        //cache 무효화
        response.setHeader("pragma","no-cache");
        response.setHeader("my-header","hello");
        //[header의 편의 매서드]
        //content(response);
       // cookie(response);


        //[message body setting]
        //response.getWriter or response.getInputStream
        PrintWriter writer = response.getWriter();
        writer.println("안녕하세요");

        redirect(response);
    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2  writer의 내용의 길이(contenet 내용의 길이)
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }
    //웹브라우저에서 쿠키  설정 후   refresh하면 웹브라우저에서 서버로 쿠키를 보내준다(인증에 사용)
    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600"); // "쿠키 세트" , "키 밸루" "쿠키 유효 기간"
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }
    //http메시지에서 상태 코드가 300 번대이고, location정보가 주어져 브라우저에서 redirect해준다.
    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html //(redirect goal)
        response.setStatus(HttpServletResponse.SC_FOUND); //302
        response.setHeader("Location", "/basic/hello-form.html");
       // response.sendRedirect("/basic/hello-form.html");
    }
}
