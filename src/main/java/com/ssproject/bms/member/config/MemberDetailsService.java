package com.ssproject.bms.member.config;

import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String mberEmail) throws UsernameNotFoundException {

        MemberEntity mberInfo = memberRepository.findByMberEmail(mberEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + mberEmail + " not found"));
        return new org.springframework.security.core.userdetails.User(mberInfo.getMberEmail(),
                mberInfo.getMberPw(), getAuthorities(mberInfo));

    }

    private static Collection<? extends GrantedAuthority> getAuthorities(MemberEntity mberInfo) {
        String[] mberAuthors = mberInfo.getAuthors()
                .stream()
                .map((author) -> author.getAuthorNm())
                .toArray(String[]::new);

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(mberAuthors);
        return authorities;
    }
}
