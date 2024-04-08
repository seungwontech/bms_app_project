package com.ssproject.bms.member.service;


import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.ssproject.bms.member.dto.OAuthAttributes;
import com.ssproject.bms.member.dto.SessionUser;
import com.ssproject.bms.member.entity.AuthorEntity;
import com.ssproject.bms.member.entity.MemberAuthorEntity;
import com.ssproject.bms.member.entity.MemberEntity;
import com.ssproject.bms.member.repository.AuthorRepository;
import com.ssproject.bms.member.repository.MemberAuthorRepository;
import com.ssproject.bms.member.repository.MemberRepository;
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


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest); // Oath2 정보를 가져옴

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 소셜 정보 가져옴
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();  //OAuth2 로그인 시 키(PK)가 되는 값
        OAuthAttributes attributes = OAuthAttributes.of(userNameAttributeName, oAuth2User.getAttributes()); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        MemberEntity memberEntity = saveOrUpdate(attributes);
        //MemberAuthorEntity memberAuthorEntity = save(memberEntity);

        httpSession.setAttribute("memberEntity", new SessionUser(memberEntity));
//
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private MemberEntity saveOrUpdate(OAuthAttributes attributes) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMberEmail(attributes.getEmail());

        if (optionalMemberEntity.isEmpty()) {
            MemberEntity memberEntity = attributes.toEntity(bCryptPasswordEncoder.encode("1234"), 'Y');
            return memberRepository.save(memberEntity);
        }
        return optionalMemberEntity.get();
    }

    public AuthorEntity getAuthorInfo(int authorId) {
        Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findByAuthorId(authorId);
        AuthorEntity authorEntity = optionalAuthorEntity.get();
        return authorEntity;
    }

    private MemberAuthorEntity save(MemberEntity memberEntity) {
        //Optional<MemberAuthorEntity> optionalAuthorEntity = memberAuthorRepository.findByMberId(memberEntity.getMberId());
        MemberAuthorEntity memberAuthorEntity = new MemberAuthorEntity();
        memberAuthorEntity.setAuthorEntity(getAuthorInfo(1));
        memberEntity.getMemberAuthors().add(memberAuthorEntity);
        memberAuthorEntity.setMemberEntity(memberEntity);
        memberAuthorRepository.save(memberAuthorEntity);
        return memberAuthorRepository.save(memberAuthorEntity);
    }

}