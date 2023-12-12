package com.ssproject.bms.board.dto;

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

}
