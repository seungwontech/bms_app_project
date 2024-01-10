package com.ssproject.bms.board.entity;

import com.ssproject.bms.board.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "bbs_answer_tbl")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;
    private String mberId;
    private String answerCn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nttId")
    private BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setMberId(commentDTO.getMberId());
        commentEntity.setAnswerCn(commentDTO.getAnswerCn());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}