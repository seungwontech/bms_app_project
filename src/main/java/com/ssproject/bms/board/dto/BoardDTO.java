package com.ssproject.bms.board.dto;

import com.ssproject.bms.board.entity.BoardEntity;
import com.ssproject.bms.board.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private int nttId;
    private String nttSj;
    private String nttCn;
    private int inqlreCo;
    private char useYn;
    private LocalDateTime regDt;
    private LocalDateTime chgDt;

    private List<MultipartFile> boardFile;
    private List<String> originalFileNm;    // 원본 파일 이름
    private List<String> storedFileNm;      // 서버 저장용 파일 이름
    private char atchFileYn;            // 파일 첨부 여부(첨부 Y, 미첨부 N)

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
        boardDTO.setUseYn(boardEntity.getUseYn());
        boardDTO.setAtchFileYn(boardEntity.getAtchFileYn());

        if (boardEntity.getAtchFileYn() == 'Y') {
            List<String> originalFileNmList = new ArrayList<>();
            List<String> storedFileNmList = new ArrayList<>();
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()){
                originalFileNmList.add(boardFileEntity.getOriginalFileNm());
                storedFileNmList.add(boardFileEntity.getStoredFileNm());
                //boardDTO.setOriginalFileNm(boardEntity.getBoardFileEntityList().get(0).getOriginalFileNm());
                //boardDTO.setStoredFileNm(boardEntity.getBoardFileEntityList().get(0).getStoredFileNm());
            }
            boardDTO.setOriginalFileNm(originalFileNmList);
            boardDTO.setStoredFileNm(storedFileNmList);
        }

        return boardDTO;
    }
}
