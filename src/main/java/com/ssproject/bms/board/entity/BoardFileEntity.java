package com.ssproject.bms.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "bbs_file_tbl")
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;
    private String originalFileNm;
    private String storedFileNm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nttId")
    private BoardEntity boardEntity;

    public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileNm, String storedFileNm) {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileNm(originalFileNm);
        boardFileEntity.setStoredFileNm(storedFileNm);
        boardFileEntity.setBoardEntity(boardEntity);
        return boardFileEntity;
    }
}
