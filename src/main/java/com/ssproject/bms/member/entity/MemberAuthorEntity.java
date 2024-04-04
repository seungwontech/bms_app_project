package com.ssproject.bms.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "mber_author_tbl")
public class MemberAuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mberAuthorId;

    @ManyToOne
    @JoinColumn(name = "mber_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;

    /*
    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<MemberEntity> members;
    */

}
