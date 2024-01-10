package com.ssproject.bms.board.dto;

import com.ssproject.bms.board.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private int answerId;
    private String mberId;
    private String answerCn;
    private int nttId;
    private LocalDateTime regDt;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, int nttId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAnswerId(commentEntity.getAnswerId());
        commentDTO.setMberId(commentEntity.getMberId());
        commentDTO.setAnswerCn(commentEntity.getAnswerCn());
        commentDTO.setRegDt(commentEntity.getRegDt());
        commentDTO.setNttId(nttId);
        return commentDTO;
    }
}