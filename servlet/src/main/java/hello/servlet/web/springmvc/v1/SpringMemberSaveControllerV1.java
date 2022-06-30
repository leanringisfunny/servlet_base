package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Controller
public class SpringMemberSaveControllerV1 {

    MemberRepository memberRepository= MemberRepository.getInstance();
    //Controller가 frontController에서 만든 paramMap을 받는게 아니라 frontcontroller에서 매핑된 주소 호출 시 매핑된  컨트롤러 시행
    // 뷰 리졸버에 model and view 반환
    @RequestMapping("/springmvc/v1/members/save")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {
        String userName=request.getParameter("username");
        int  age=Integer.parseInt(request.getParameter("age"));

        Member member=new Member(userName,age);
        memberRepository.save(member);

        ModelAndView view = new ModelAndView("save-result");
        view.addObject("member",member);
        return view;
    }
}
