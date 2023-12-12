package com.ssproject.bms.board.controller;

import com.ssproject.bms.board.dto.BoardDTO;
import com.ssproject.bms.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/reg")
    public String regForm() {
        return "board/board_reg";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute BoardDTO boardDTO) {
        boardService.reg(boardDTO);
        return "board/board_list";
    }

    @GetMapping("/")
    public String boardList(Model model) {
        List<BoardDTO> list = boardService.findAll();
        model.addAttribute("list", list);
        return "board/board_list";
    }
}
