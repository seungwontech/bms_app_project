package com.ssproject.bms.board.repository;


import com.ssproject.bms.board.entity.BoardEntity;
import com.ssproject.bms.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByBoardEntityOrderByAnswerIdDesc(BoardEntity boardEntity);
}