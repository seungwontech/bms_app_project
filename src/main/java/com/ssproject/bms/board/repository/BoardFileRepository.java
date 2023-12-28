package com.ssproject.bms.board.repository;

import com.ssproject.bms.board.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Integer> {
}