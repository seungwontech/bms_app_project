package com.ssproject.bms.member.repository;

import com.ssproject.bms.member.entity.MemberAuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAuthorRepository extends JpaRepository<MemberAuthorEntity, Integer> {
    List<MemberAuthorEntity> findByMemberEntityMberId(int mberId);
}
