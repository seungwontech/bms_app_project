package com.ssproject.bms.board.dto;

import com.ssproject.bms.board.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private int nttId;
    private String nttSj;
    private String nttCn;
    private int inqlreCo;
    private char useYn;
    private String atchFileId;
    private LocalDateTime regDt;
    private LocalDateTime chgDt;

    public BoardDTO(int nttId, String nttSj, String nttCn, int inqlreCo, LocalDateTime regDt) {
        this.nttId = nttId;
        this.nttSj = nttSj;
        this.nttCn = nttCn;
        this.inqlreCo = inqlreCo;
        this.regDt = regDt;
    }


    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNttId(boardEntity.getNttId());
        boardDTO.setNttSj(boardEntity.getNttSj());
        boardDTO.setNttCn(boardEntity.getNttCn());
        boardDTO.setInqlreCo(boardEntity.getInqlreCo());
        boardDTO.setRegDt(boardEntity.getRegDt());
        boardDTO.setChgDt(boardEntity.getChgDt());

        return boardDTO;
    }
}
