package com.ssproject.bms.member.controller;

import com.ssproject.bms.member.dto.MemberDTO;
import com.ssproject.bms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final HttpSession httpSession;
    private final MemberService memberService;

    @GetMapping("/reg")
    public String regForm() {
        return "member/member_reg";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute MemberDTO memberDTO) {
        memberService.reg(memberDTO);
        return "redirect:/member/member_login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/member_login";
    }
}
