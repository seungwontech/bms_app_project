package com.ssproject.bms.board.service;


import com.ssproject.bms.board.dto.BoardDTO;
import com.ssproject.bms.board.entity.BoardEntity;
import com.ssproject.bms.board.entity.BoardFileEntity;
import com.ssproject.bms.board.repository.BoardFileRepository;
import com.ssproject.bms.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void reg(BoardDTO boardDTO) throws IOException {
        boolean isEmptyFile = false;

        for (MultipartFile boardFile : boardDTO.getBoardFile()) {
            if (!boardFile.isEmpty()) {
                isEmptyFile = true;
            }
        }

        if (!isEmptyFile) {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntity);
        } else {
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            int saveNttId = boardRepository.save(boardEntity).getNttId();
            BoardEntity board = boardRepository.findById(saveNttId).get();

            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                if (boardFile.isEmpty()) {
                    continue;
                }
                String originalFileNm = boardFile.getOriginalFilename();
                String storedFileNm = System.currentTimeMillis() + "_" + originalFileNm;
                String savePath = "C:/jpaUploadFile/" + storedFileNm;
                boardFile.transferTo(new File(savePath));

                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFileNm, storedFileNm);
                boardFileRepository.save(boardFileEntity);
            }
        }
    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(int nttId) {
        boardRepository.updateHits(nttId);
    }

    @Transactional
    public BoardDTO findById(int nttId) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(nttId);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO mod(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getNttId());
    }

    public void delete(int nttId) {
        boardRepository.deleteById(nttId);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "nttId")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getNttId(), board.getNttSj(), board.getNttCn(), board.getInqlreCo(), board.getRegDt()));
        return boardDTOS;
    }
}
