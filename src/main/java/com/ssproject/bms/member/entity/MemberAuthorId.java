package com.ssproject.bms.member.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MemberAuthorId implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    private int mberId;

    @EqualsAndHashCode.Include
    @Id
    private int authorId;
}
