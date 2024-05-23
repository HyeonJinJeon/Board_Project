package org.example.board_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.board_project.domain.Board;
import org.example.board_project.service.BoardService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    //목록 보기
    @GetMapping("/list")
    public String list(Model model){
        Iterable<Board> boards =  boardService.findAllPost();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    //상세 보기
    @GetMapping("/view/{id}")
    public String detail(@PathVariable Long id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    //글 쓰기 --요청 2개
    @GetMapping("updateform/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "board/updateform";
    }

    @PostMapping("/updateform")
    public String updaate(@ModelAttribute Board board){
        boardService.updateBoard(board);
        return "redirect:/list";
    }

    //수정  --요청 2개

    //삭제 --비밀번호를 입력해야하므로 요청 2개
}
