package com.ssproject.bms.member.service;

import com.ssproject.bms.member.dto.CustomUserDetails;
import com.ssproject.bms.member.dto.MemberDTO;
import com.ssproject.bms.member.entity.AuthorEntity;
import com.ssproject.bms.member.entity.MemberAuthorEntity;
import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.repository.AuthorRepository;
import com.ssproject.bms.member.repository.MemberAuthorRepository;
import com.ssproject.bms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthorRepository authorRepository;
    private final MemberAuthorRepository memberAuthorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 권한 정보 조회
     *
     * @param authorId
     * @return
     */
    public AuthorEntity getAuthorInfo(int authorId) {
        return authorRepository.findByAuthorId(authorId).orElseThrow();
    }

    /**
     * 회원가입 처리
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
    public CustomUserDetails loadUserByUsername(String mberEmail) throws UsernameNotFoundException {
        // 이메일로 회원 정보 조회
        MemberEntity mberInfo = memberRepository.findByMberEmail(mberEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + mberEmail + " not found"));

        return new CustomUserDetails(mberInfo, getAuthorities(mberInfo));
    }

    /**
     * 회원 권한 조회
     *
     * @param mberInfo
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(MemberEntity mberInfo) {
        List<GrantedAuthority> authorities = mberInfo.getMemberAuthors().stream()
                .map(memberAuthor -> new SimpleGrantedAuthority(memberAuthor.getAuthorEntity().getAuthorNm()))
                .collect(Collectors.toList());
        return authorities;
    }

    /**
     * 현재 사용자 정보 조회
     *
     * @return
     */
    public Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return ((CustomUserDetails) principal).getMemberEntity().getMberId();
        }
        return null;
    }
}
