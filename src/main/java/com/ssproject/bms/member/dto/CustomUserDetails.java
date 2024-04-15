package com.ssproject.bms.member.dto;


import com.ssproject.bms.member.entity.MemberEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

    private MemberEntity memberEntity;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes; // OAuth2 사용자의 추가 속성

    // 기본 생성자
    public CustomUserDetails(MemberEntity memberEntity, Collection<? extends GrantedAuthority> authorities) {
        this.memberEntity = memberEntity;
        this.authorities = authorities;
    }

    // OAuth2 사용자 속성을 포함하는 생성자
    public CustomUserDetails(MemberEntity memberEntity, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.memberEntity = memberEntity;
        this.authorities = authorities;
        this.attributes = attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // 사용자의 권한 목록 반환
    }

    @Override
    public String getPassword() {
        return memberEntity.getMberPw(); // 사용자의 패스워드 반환
    }

    @Override
    public String getUsername() {
        return memberEntity.getMberNm(); // 사용자명 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 반환 (항상 true)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 반환 (항상 true)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(패스워드)의 만료 여부 반환 (항상 true)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 반환 (항상 true)
    }

    // Methods from OAuth2User interface

    @Override
    public Map<String, Object> getAttributes() {
        return attributes; // OAuth2 사용자의 추가 속성 반환
    }

    @Override
    public String getName() {
        return null;
    }
}


