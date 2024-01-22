package com.ssproject.bms.member.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "mberId")
@ToString
@Table(name="mber_author_tbl")
public class MemberRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mberId;

    private int authorId;

}
