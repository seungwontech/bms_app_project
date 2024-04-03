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
@IdClass(MemberAuthorId.class)
@Table(name="mber_author_tbl")
public class MemberAuthorEntity {
    @Id
    private int mberId;
    @Id
    private int authorId;

    @ManyToOne
    @JoinColumn(name = "mberId",  updatable = false, insertable = false)
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "authorId", updatable = false, insertable = false)
    private AuthorEntity authorEntity;

  /*  @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<MemberEntity> members;*/

}
