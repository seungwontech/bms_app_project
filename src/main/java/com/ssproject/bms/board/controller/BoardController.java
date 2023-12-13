package com.ssproject.bms.board.controller;

import com.ssproject.bms.board.dto.BoardDTO;
import com.ssproject.bms.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;


    /**
     * 게시물 등록 이동
     * @return
     */
    @GetMapping("/reg")
    public String regForm() {
        return "board/board_reg";
    }

    /**
     * 게시물 등록
     * @param boardDTO
     * @return
     */
    @PostMapping("/reg")
    public String reg(@ModelAttribute BoardDTO boardDTO) {
        boardService.reg(boardDTO);
        return "board/board_list";
    }

    /**
     * 게시물 목록 조회
     * @param model
     * @return
     */
    @GetMapping("/")
    public String boardList(Model model) {
        List<BoardDTO> list = boardService.findAll();
        model.addAttribute("list", list);
        return "board/board_list";
    }

    @GetMapping("/{nttId}")
    public String findById(@PathVariable int nttId, Model model) {
        boardService.updateHits(nttId);
        BoardDTO boardDTO = boardService.findById(nttId);
        model.addAttribute("board", boardDTO);
        return "board/board_detail";
    }

}
