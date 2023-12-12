package com.ssproject.bms.board.entity;

import com.ssproject.bms.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private String atchFileId;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setNttId(boardDTO.getNttId());
        boardEntity.setNttSj(boardDTO.getNttSj());
        boardEntity.setNttCn(boardDTO.getNttCn());
/*        boardEntity.setUseYn(boardDTO.getUseYn());
        boardEntity.setInqlreCo(boardDTO.getInqlreCo());*/
        return boardEntity;
    }


}
