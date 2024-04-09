package com.ssproject.bms.member.service;

import com.ssproject.bms.member.dto.MemberDTO;
import com.ssproject.bms.member.entity.AuthorEntity;
import com.ssproject.bms.member.entity.MemberAuthorEntity;
import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.repository.AuthorRepository;
import com.ssproject.bms.member.repository.MemberAuthorRepository;
import com.ssproject.bms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthorRepository authorRepository;
    private final MemberAuthorRepository memberAuthorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthorEntity getAuthorInfo(int authorId) {
        return authorRepository.findByAuthorId(authorId).orElseThrow();
    }

    /**
     * 회원가입
     *
     * @param memberDTO
     */
    public void reg(MemberDTO memberDTO) {
        memberDTO.setMberPw(bCryptPasswordEncoder.encode(memberDTO.getMberPw()));

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);

        MemberAuthorEntity memberAuthorEntity = new MemberAuthorEntity();
        memberAuthorEntity.setAuthorEntity(getAuthorInfo(memberDTO.getAuthorId()));
        memberEntity.getMemberAuthors().add(memberAuthorEntity);
        memberRepository.save(memberEntity);

        memberAuthorEntity.setMemberEntity(memberEntity);
        memberAuthorRepository.save(memberAuthorEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String mberEmail) throws UsernameNotFoundException {

        MemberEntity mberInfo = memberRepository.findByMberEmail(mberEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + mberEmail + " not found"));
        return new org.springframework.security.core.userdetails.User(mberInfo.getMberEmail(),
                mberInfo.getMberPw(), getAuthorities(mberInfo));

    }

    private static Collection<? extends GrantedAuthority> getAuthorities(MemberEntity mberInfo) {
        String[] mberAuthors = mberInfo.getMemberAuthors()
                .stream()
                .map((author) -> author.getAuthorEntity().getAuthorNm())
                .toArray(String[]::new);

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(mberAuthors);
        return authorities;
    }
}
