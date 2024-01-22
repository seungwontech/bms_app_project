package com.ssproject.bms;

import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.entity.MemberRoleEntity;
import com.ssproject.bms.repository.MemberRepositoryTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class MemberTests {
    @Autowired
    private MemberRepositoryTests memberRepositoryTests;

    @Test
    public void insertTest() {
        for (int i = 0; i < 100; i++) {
            MemberEntity member = new MemberEntity();
            member.setMberId(i);
            member.setMberPw("pw" + i);
            member.setMberEmail("hihi@" + i);
            member.setUseYn('Y');
            member.setMberNm("이승원");
            MemberRoleEntity role = new MemberRoleEntity();
            if (i <= 80) {
                role.setAuthorId(1);
            } else if (i <= 90) {
                role.setAuthorId(2);
            }
            member.setRoles(Arrays.asList(role));
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