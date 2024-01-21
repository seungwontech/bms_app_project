package com.ssproject.bms.member.dto;

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
}