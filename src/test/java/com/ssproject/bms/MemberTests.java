package com.ssproject.bms;

import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.entity.MemberRoleEntity;
import com.ssproject.bms.repository.MemberRepositoryTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootTest
public class MemberTests {
    @Autowired
    private MemberRepositoryTests memberRepositoryTests;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void insertTest() {
        for (int i = 5; i < 6; i++) {
            MemberEntity member = new MemberEntity();
            member.setMberId(i);
            member.setMberPw(passwordEncoder.encode("123"));
            member.setMberEmail("hihi@" + i);
            member.setUseYn('Y');
            member.setMberNm("이승원");
            MemberRoleEntity role = new MemberRoleEntity();
            System.out.println(role.getAuthorNm());
            if (i <= 80) {
                role.setAuthorId(2);
            } else if (i <= 90) {
                role.setAuthorNm("admin");
            }
            member.getAuthors().add(role);
            System.out.println("member : "+member);
            memberRepositoryTests.save(member);
        }
    }

//    @Test
//    public void testMember() {
//        Optional<MemberEntity> result = Optional.ofNullable(memberRepository.findOne());
//        result.ifPresent(member -> log.info("member " + member));
//    }
}