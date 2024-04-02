package com.ssproject.bms.member.dto;

import com.ssproject.bms.member.entity.MemberEntity;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    SessionUser() {
    }

    public SessionUser(MemberEntity memberEntity) {
        this.mberNm = memberEntity.getMberNm();
        this.mberEmail = memberEntity.getMberEmail();
    }

    private String mberNm;

    private String mberEmail;

}