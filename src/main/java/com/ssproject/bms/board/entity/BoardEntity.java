package com.ssproject.bms.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssproject.bms.board.dto.BoardDTO;
import com.ssproject.bms.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bbs_tbl")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nttId;
    private String nttSj;
    private String nttCn;
    private int inqlreCo;
    private char useYn;
    private char atchFileYn; // Y or N

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "mber_id")
    @JsonIgnore
    private MemberEntity member;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setNttId(boardDTO.getNttId());
        boardEntity.setNttSj(boardDTO.getNttSj());
        boardEntity.setNttCn(boardDTO.getNttCn());
        boardEntity.setInqlreCo(0);
        boardEntity.setUseYn('Y');
        boardEntity.setAtchFileYn('N');

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setNttId(boardDTO.getNttId());
        boardEntity.setNttSj(boardDTO.getNttSj());
        boardEntity.setNttCn(boardDTO.getNttCn());
        boardEntity.setInqlreCo(0);
        boardEntity.setUseYn('Y');
        boardEntity.setAtchFileYn('Y'); // 파일 있음.

        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setNttId(boardDTO.getNttId());
        boardEntity.setNttSj(boardDTO.getNttSj());
        boardEntity.setNttCn(boardDTO.getNttCn());
        boardEntity.setInqlreCo(boardDTO.getInqlreCo());
        boardEntity.setUseYn(boardDTO.getUseYn());
        boardEntity.setAtchFileYn(boardDTO.getAtchFileYn());

        return boardEntity;
    }
}
