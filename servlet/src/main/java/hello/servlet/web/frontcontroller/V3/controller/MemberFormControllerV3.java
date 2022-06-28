package hello.servlet.web.frontcontroller.V3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3{

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
