package com.ssproject.bms.member.dto;

import com.ssproject.bms.member.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private int mberId;
    private String mberEmail;
    private String mberPw;
    private String mberNm;
    private char useYn;
    private LocalDateTime regDt;
    private LocalDateTime chgDt;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMberId(memberEntity.getMberId());
        memberDTO.setMberEmail(memberEntity.getMberEmail());
        memberDTO.setMberPw(memberEntity.getMberPw());
        memberDTO.setMberNm(memberEntity.getMberNm());
        return memberDTO;
    }


}