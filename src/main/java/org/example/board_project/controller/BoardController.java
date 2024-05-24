package org.example.board_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.board_project.domain.Board;
import org.example.board_project.service.BoardService;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    //목록 보기
    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int size){

        Pageable pageable = PageRequest.of(page -1 , size);
        Page<Board> boards = boardService.findAllPost(pageable);
//        Iterable<Board> boards =  boardService.findAllPost();
        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);
        return "board/list";
    }

    //상세 보기
    @GetMapping("/view")
    public String detail(@RequestParam Long id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    //글 쓰기 --요청 2개
    @GetMapping("/writeform")
    public String writeform(Model model){
        model.addAttribute("board", new Board());
        return "board/writeform";
    }
    @PostMapping("/writeform")
    public String write(@ModelAttribute Board board) {
        boardService.saveBoard(board);
        return "redirect:/list";
    }
    //수정  --요청 2개
    @GetMapping("/updateform")
    public String updateForm(@RequestParam Long id, Model model) {
        Board board = boardService.findById(id);

        // 한국 시간으로 포맷
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        String formattedCreatedAt = board.getCreatedAt().atZone(koreaZone).format(formatter);
        String formattedUpdatedAt = board.getUpdatedAt() != null ? board.getUpdatedAt().atZone(koreaZone).format(formatter) : "";

        model.addAttribute("board", board);
        model.addAttribute("formattedCreatedAt", formattedCreatedAt);
        model.addAttribute("formattedUpdatedAt", formattedUpdatedAt);

        return "board/updateform";
    }

    @PostMapping("/updateform")
    public String update(@ModelAttribute Board board, @RequestParam String password, Model model) {
        if (boardService.checkPassword(board.getId(), password)) {
            ZoneId koreaZone = ZoneId.of("Asia/Seoul");
            board.setUpdatedAt(LocalDateTime.now(koreaZone));
            boardService.updateBoard(board);
            return "redirect:/list";
        } else {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");

            // 한국 시간으로 포맷
            ZoneId koreaZone = ZoneId.of("Asia/Seoul");
            String formattedCreatedAt = board.getCreatedAt().atZone(koreaZone).format(formatter);
            String formattedUpdatedAt = board.getUpdatedAt() != null ? board.getUpdatedAt().atZone(koreaZone).format(formatter) : "";

            model.addAttribute("formattedCreatedAt", formattedCreatedAt);
            model.addAttribute("formattedUpdatedAt", formattedUpdatedAt);

            return "board/updateform";
        }
    }

    //삭제 --비밀번호를 입력해야하므로 요청 2개
    @GetMapping("/deleteform")
    public String deleteform(@RequestParam Long id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "board/deleteform";
    }
    @PostMapping("/deleteform")
    public String delete(@ModelAttribute Board board, @RequestParam String password, Model model) {
        if (boardService.checkPassword(board.getId(), password)) {
            boardService.deleteBoard(board);
            return "redirect:/list";
        } else {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("board", board);
            return "board/deleteform";
        }
    }
}
