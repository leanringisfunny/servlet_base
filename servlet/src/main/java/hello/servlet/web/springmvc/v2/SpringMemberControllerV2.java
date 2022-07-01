package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();


    @RequestMapping("/new-form")
    public ModelAndView newForm(){
        return new ModelAndView("new-form");
    }

    @RequestMapping
    public ModelAndView members() {
        ArrayList<Member> members = memberRepository.findAll();
        ModelAndView mv =new ModelAndView("members");

        mv.addObject("members",members);
        return mv;
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        String userName=request.getParameter("username");
        int  age=Integer.parseInt(request.getParameter("age"));

        Member member=new Member(userName,age);
        memberRepository.save(member);

        ModelAndView view = new ModelAndView("save-result");
        view.addObject("member",member);
        return view;
    }
}
