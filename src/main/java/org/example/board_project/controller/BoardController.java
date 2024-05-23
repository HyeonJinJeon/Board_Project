package org.example.board_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.board_project.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    //목록 보기
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute();
    }

    //상세 보기

    //글 쓰기 --요청 2개

    //수정  --요청 2개

    //삭제 --비밀번호를 입력해야하므로 요청 2개
}
