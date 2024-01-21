package com.ssproject.bms.member.repository;

import com.ssproject.bms.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    Optional<MemberEntity> findByMberEmail(String mberEmail);
}
