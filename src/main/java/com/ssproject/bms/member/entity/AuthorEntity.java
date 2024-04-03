package com.ssproject.bms.member.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name="author_tbl")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;
    private String authorNm;

    @OneToMany(mappedBy = "authorEntity")
    private List<MemberAuthorEntity> MemberAuthors = new ArrayList<>();
}
