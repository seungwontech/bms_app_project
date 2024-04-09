package com.ssproject.bms.member.service;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.ssproject.bms.member.dto.OAuthAttributes;
import com.ssproject.bms.member.dto.SessionUser;
import com.ssproject.bms.member.entity.AuthorEntity;
import com.ssproject.bms.member.entity.MemberAuthorEntity;
import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.repository.AuthorRepository;
import com.ssproject.bms.member.repository.MemberAuthorRepository;
import com.ssproject.bms.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private final AuthorRepository authorRepository;

    private final MemberAuthorRepository memberAuthorRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final HttpSession httpSession;

    /**
     * OAuth2UserRequest를 기반으로 사용자 정보를 가져오고, 로그인 처리
     *
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(userNameAttributeName, oAuth2User.getAttributes());

        MemberEntity memberEntity = saveOrUpdate(attributes);

        httpSession.setAttribute("memberEntity", new SessionUser(memberEntity));
        Collection<? extends GrantedAuthority> authorities = getAuthorities(memberEntity);


        return new DefaultOAuth2User(authorities,
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * 사용자의 권한 설정
     *
     * @param memberEntity
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(MemberEntity memberEntity) {
        return Collections.singleton(new SimpleGrantedAuthority(memberEntity.getMemberAuthors().get(0).getAuthorEntity().getAuthorNm()));
    }

    /**
     * OAuthAttributes 정보로 회원 생성 또는 업데이트
     *
     * @param attributes
     * @return
     */
    private MemberEntity saveOrUpdate(OAuthAttributes attributes) {
        MemberEntity memberEntity = memberRepository.findByMberEmail(attributes.getEmail()).orElse(null);

        if (memberEntity == null) {
            // 새로운 회원이면 회원 정보 생성
            memberEntity = attributes.toEntity(bCryptPasswordEncoder.encode("1234"), 'Y');
            MemberAuthorEntity memberAuthorEntity = new MemberAuthorEntity();
            memberAuthorEntity.setAuthorEntity(getAuthorInfo(1));
            memberEntity.getMemberAuthors().add(memberAuthorEntity);
            memberRepository.save(memberEntity);
            memberAuthorEntity.setMemberEntity(memberEntity);
            memberAuthorRepository.save(memberAuthorEntity);
        } else {
            memberEntity.setMemberAuthors(getMemberAuthors(memberEntity.getMberId()));
        }

        return memberEntity;
    }

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
     * 가입된 회원 권한 정보 조회
     *
     * @param mberId
     * @return
     */
    public List<MemberAuthorEntity> getMemberAuthors(int mberId) {
        return memberAuthorRepository.findByMemberEntityMberId(mberId);
    }
}