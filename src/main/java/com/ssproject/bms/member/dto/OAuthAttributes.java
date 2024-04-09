package com.ssproject.bms.member.dto;

import java.util.Map;

import com.ssproject.bms.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;


    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    /**
     * OAuth2UserRequest에서 받은 사용자 이름 속성 이름과 속성 맵을 기반으로 OAuthAttributes 객체를 생성
     *
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        return ofNaver("email", attributes); // userNameAttributeName 네이버 지원하지않아서 id, name, email 하드코딩으로 넣어도 된다.
    }

    /**
     * 네이버에서 받은 사용자 정보 맵을 기반으로 OAuthAttributes 객체를 생성
     *
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .build();
    }

    /**
     * OAuthAttributes를 기반으로 MemberEntity 생성
     *
     * @param mberPw
     * @param useYn
     * @return
     */
    public MemberEntity toEntity(String mberPw, char useYn) {
        return MemberEntity.createMemberEntity()
                .mberNm(name)
                .mberEmail(email)
                .useYn(useYn)
                .mberPw(mberPw)
                .build();
    }
}