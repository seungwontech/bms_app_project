package com.ssproject.bms.board.service;


import com.ssproject.bms.board.dto.BoardDTO;
import com.ssproject.bms.board.entity.BoardEntity;
import com.ssproject.bms.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void reg(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
}
