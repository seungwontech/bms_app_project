package com.ssproject.bms.board.service;

import com.ssproject.bms.board.dto.CommentDTO;
import com.ssproject.bms.board.entity.BoardEntity;
import com.ssproject.bms.board.entity.CommentEntity;
import com.ssproject.bms.board.repository.BoardRepository;
import com.ssproject.bms.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public int reg(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getNttId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getAnswerId();
        } else {
            return 0;
        }
    }

    public List<CommentDTO> findAll(int nttId) {
        BoardEntity boardEntity = boardRepository.findById(nttId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByAnswerIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, nttId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

}