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

    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        return ofNaver("id", attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .build();
    }

    public MemberEntity toEntity(String mberPw, char useYn) {
        return MemberEntity.createMemberEntity()
                .mberNm(name)
                .mberEmail(email)
                .useYn(useYn)
                .mberPw(mberPw)
                .build();
    }
}