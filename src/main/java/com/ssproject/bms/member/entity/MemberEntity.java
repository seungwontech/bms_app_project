package com.ssproject.bms.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssproject.bms.board.entity.BoardEntity;
import com.ssproject.bms.member.dto.MemberDTO;
import com.ssproject.bms.board.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "mber_tbl")
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mberId;

    @Column(unique = true)
    private String mberEmail;
    private String mberPw;
    private String mberNm;
    private char useYn;  // Y or N


    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMberEmail(memberDTO.getMberEmail());
        memberEntity.setMberPw(memberDTO.getMberPw());
        memberEntity.setMberNm(memberDTO.getMberNm());
        memberEntity.setUseYn('Y');

        return memberEntity;
    }


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "mber_author_tbl"
            , joinColumns = @JoinColumn(name = "mber_id")
            , inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<MemberAuthorEntity> authors = new ArrayList<>();


    @OneToMany(mappedBy ="member", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BoardEntity> boards = new ArrayList<>();
}