package com.ssproject.bms.repository;

import com.ssproject.bms.member.entity.MemberEntity;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepositoryTests extends CrudRepository<MemberEntity, Integer> {
}
