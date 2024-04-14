package com.ssproject.bms.member.dto;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private int mberId;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, int mberId, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.mberId = mberId;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // 사용자의 권한 목록 반환
    }

    @Override
    public String getPassword() {
        return password; // 사용자의 패스워드 반환
    }

    @Override
    public String getUsername() {
        return username; // 사용자명 반환
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
}


