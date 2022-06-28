package hello.servlet.web.frontcontroller.V3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3{
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age") );
        Member member = new Member(username, age);
        memberRepository.save(member);

        //Model에 데이터를 보관
        ModelView modelView = new ModelView("save-result");//key value
        modelView.getModel().put("member",member);

        return  modelView;
    }
}
