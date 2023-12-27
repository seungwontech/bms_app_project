package com.ssproject.bms.board.controller;

import com.ssproject.bms.board.dto.BoardDTO;
import com.ssproject.bms.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
     * 게시물 등록 페이지 이동
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
    @GetMapping("/list")
    public String boardList(Model model, @PageableDefault(page=1) Pageable pageable) {
        //List<BoardDTO> list = boardService.findAll();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("list", boardList);
        return "board/board_list";
    }

    /**
     * 게시물 상세 조회
     * @param nttId
     * @param model
     * @return
     */
    @GetMapping("/{nttId}")
    public String findById(@PathVariable int nttId, Model model, @PageableDefault(page=1) Pageable pageable) {
        boardService.updateHits(nttId);
        BoardDTO boardDTO = boardService.findById(nttId);
        model.addAttribute("boardInfo", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "board/board_detail";
    }

    /**
     * 게시물 수정 페이지 이동
     * @param nttId
     * @param model
     * @return
     */
    @GetMapping("/mod/{nttId}")
    public String modForm(@PathVariable int nttId, Model model) {
        BoardDTO boardDTO = boardService.findById(nttId);
        model.addAttribute("boardInfo", boardDTO);
        return "board/board_mod";
    }

    /**
     * 게시물 수정
     * @param boardDTO
     * @param model
     * @return
     */
    @PostMapping("/mod")
    public String mod(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.mod(boardDTO);
        model.addAttribute("board", board);
        return "board/board_detail";
    }

    /**
     * 게시물 삭제
     * @param nttId
     * @return
     */
    @GetMapping("/del/{nttId}")
    public String del(@PathVariable int nttId) {
        boardService.delete(nttId);
        return "redirect:/board/board_list";
    }

    /**
     * 페이징
     * @param pageable
     * @param model
     * @return
     */
/*    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
//        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개
        System.out.println("boardList : "+ boardList);

        model.addAttribute("list", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/board_list";

    }*/
}
