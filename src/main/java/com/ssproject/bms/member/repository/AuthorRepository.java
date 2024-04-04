package com.ssproject.bms.member.repository;

import com.ssproject.bms.member.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {
    Optional<AuthorEntity> findByAuthorId(int authorId);
}
