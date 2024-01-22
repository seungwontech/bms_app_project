package com.ssproject.bms.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name="author_tbl")
public class MemberRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;
    private String authorNm;


    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<MemberEntity> members;

}
