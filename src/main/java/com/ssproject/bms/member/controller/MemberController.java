package com.ssproject.bms.member.controller;

import com.ssproject.bms.member.dto.MemberDTO;
import com.ssproject.bms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/reg")
    public String regForm() {
        return "member/member_reg";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute MemberDTO memberDTO) {
        memberService.reg(memberDTO);
        return "member/member_reg";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "member/member_login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO  loginResult = memberService.login(memberDTO);

        if (loginResult != null){
            session.setAttribute("mberEmail", loginResult.getMberEmail());
            session.setAttribute("mberNm", loginResult.getMberNm());
            return "redirect:/board/list";
        } else {
            return "member/member_login";
        }
    }
}
