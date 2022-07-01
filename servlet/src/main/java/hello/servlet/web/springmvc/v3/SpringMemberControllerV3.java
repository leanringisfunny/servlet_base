package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    //new-form String 으로 반환하면 어뎁터에서 알아서 처리 끝// HTTP METHOD도 제한 할 수 있다(요청메서드 제한)

    //@RequestMapping(value="/new-form", method= RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm(){
        return "new-form";
    }

    //@RequestMapping(method= RequestMethod.GET)
    @GetMapping
    public String members(Model model) {
        ArrayList<Member> members = memberRepository.findAll();

        model.addAttribute("members",members);
        return "members";
    }

    //RequestParam으로 인자를 받을 시 자동으로 타입을 캐스팅 시켜줄 수 있음
    //@RequestMapping(value="/save" ,method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(@RequestParam("username")String username,
                             @RequestParam("age") int age,
                             Model model) {

        Member member=new Member(username,age);
        memberRepository.save(member);
        model.addAttribute("member",member);

        return "save-result";
    }
}
