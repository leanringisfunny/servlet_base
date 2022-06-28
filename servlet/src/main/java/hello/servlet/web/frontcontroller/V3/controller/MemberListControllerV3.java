package hello.servlet.web.frontcontroller.V3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;

import java.util.ArrayList;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository= MemberRepository.getInstance();


    @Override
    public ModelView process(Map<String, String> paramMap) {
        ArrayList<Member> members = memberRepository.findAll();
        ModelView modelView =new ModelView("members");

        modelView.getModel().put("members",members);
        return modelView;
    }

}
