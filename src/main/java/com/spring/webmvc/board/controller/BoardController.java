package com.spring.webmvc.board.controller;

import com.spring.webmvc.board.domain.Board;
import com.spring.webmvc.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board") //공통 URL 진입점 설정
@Log4j2
public class BoardController {
    private  final BoardService service;
    // 게시물 목록 조회 요청 처리
    @GetMapping("/list")
    public String list(Model model) throws IOException {
        int a=10;
        List<Board> boardList = service.getList();
        log.info("/board/list GET! 요청 발생!", a);

        model.addAttribute("bList", boardList);
        return "board/list";
        /*
            TRACE - 잡다한 자잘한 로그
            DEBUG - 개발단계의 디버깅
            INFO  - 정보
            WARN  - 경고
            ERROR - 심각한 에러
         */
    }

    // 게시물 상세 조회 요청 처리
    @GetMapping("/content/{bno}")
    public String content(@PathVariable("bno") Long boardNo, Model model){
        log.info("/board/content/{} GET!! ", boardNo);
        Board board = service.getDetail(boardNo);

        model.addAttribute("b", board);
        return "board/detail";
    }
    //게시물 쓰기 화면 요청
    @GetMapping("/write")
    public String write(){
        log.info("/board/write GET!");
        return "board/write";
    }

    //게시물 등록 요청
    @PostMapping("/write")
    public String write(Board board){
        log.info("/board/write POST! - {}", board);
        boolean flag = service.insert(board);
        return flag ? "redirect:/board/list" : "redirect:/";
    }
}
