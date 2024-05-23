package org.example.board_project.service;

import lombok.RequiredArgsConstructor;
import org.example.board_project.domain.Board;
import org.example.board_project.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //목록 보기
    @Transactional(readOnly = true)
    public Iterable<Board> findAllPost() {
        return boardRepository.findAll();
    }

    //상세 보기
    @Transactional
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public void updateBoard(Board board) {
        boardRepository.save(board);
    }


    //글 쓰기 --요청 2개

    //수정  --요청 2개

    //삭제 --비밀번호를 입력해야하므로 요청 2개
}
