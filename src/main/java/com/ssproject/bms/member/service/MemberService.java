package com.ssproject.bms.member.service;

import com.ssproject.bms.member.dto.MemberDTO;
import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void reg(MemberDTO memberDTO) {
        memberDTO.setMberPw(bCryptPasswordEncoder.encode(memberDTO.getMberPw()));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMberEmail(memberDTO.getMberEmail());
        MemberDTO returnDTO = null;
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberDTO.getMberPw().equals(memberEntity.getMberPw())) {
                returnDTO = MemberDTO.toMemberDTO(memberEntity);
            }
        }
        return returnDTO;
    }
}
