package com.ssproject.bms.board.controller;

import com.ssproject.bms.board.dto.BoardDTO;
import com.ssproject.bms.board.dto.CommentDTO;
import com.ssproject.bms.board.service.BoardService;
import com.ssproject.bms.board.service.CommentService;
import com.ssproject.bms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;

    /**
     * 게시물 등록 페이지 이동
     *
     * @return
     */
    @GetMapping("/reg")
    public String regForm() {
        return "board/board_reg";
    }

    /**
     * 게시물 등록
     *
     * @param boardDTO
     * @return
     */
    @PostMapping("/reg")
    public String reg(@ModelAttribute BoardDTO boardDTO, Authentication autentication) throws IOException {
        boardService.reg(autentication.getName(), boardDTO);
        return "redirect:/board/list";
    }

    /**
     * 게시물 목록 조회
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String boardList(Model model, @PageableDefault(page = 1) Pageable pageable) {
        //List<BoardDTO> list = boardService.findAll();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage == 0 ? 1 : endPage);
        model.addAttribute("list", boardList);
        return "board/board_list";
    }

    /**
     * 게시물 상세 조회
     *
     * @param nttId
     * @param model
     * @return
     */
    @GetMapping("/{nttId}")
    public String findById(@PathVariable int nttId, Model model, @PageableDefault(page = 1) Pageable pageable) {
        boardService.updateHits(nttId);
        BoardDTO boardInfo = boardService.findById(nttId);
        List<CommentDTO> commentDTOList = commentService.findAll(nttId);

        int currentUserId = (int) memberService.getCurrentUser();
        // 현재 사용자의 아이디와 게시물 작성자의 아이디를 비교하여 수정 버튼을 표시 또는 숨김.
        boolean isCurrentUserWriter = currentUserId == boardInfo.getMberId();

        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("boardInfo", boardInfo);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("isCurrentUserWriter", isCurrentUserWriter);
        return "board/board_detail";
    }

    /**
     * 게시물 수정 페이지 이동
     *
     * @param nttId
     * @param model
     * @return
     */
    @GetMapping("/mod/{nttId}")
    public String modForm(@PathVariable int nttId, Model model, @PageableDefault(page = 1) Pageable pageable) {
        BoardDTO boardDTO = boardService.findById(nttId);
        model.addAttribute("boardInfo", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "board/board_mod";
    }

    /**
     * 게시물 수정
     *
     * @param boardDTO
     * @param model
     * @return
     */
    @PostMapping("/mod")
    public String mod(@ModelAttribute BoardDTO boardDTO, Model model, @PageableDefault(page = 1) Pageable pageable, Authentication autentication) {

        boardService.mod(autentication.getName(), boardDTO);
        int id = boardDTO.getNttId();
        int page = pageable.getPageNumber();

        return "redirect:/board/" + id + "?page=" + page;
    }

    /**
     * 게시물 삭제
     *
     * @param nttId
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/del/{nttId}")
    public String del(@PathVariable int nttId) {
        boardService.delete(nttId);
        return "redirect:/board/list";
    }
}
