package com.sparta.board.controller;


import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @PutMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto.getPassword(), requestDto);
    }

    @DeleteMapping("/boards/{id}")
    public Long deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.deleteBoard(id, requestDto.getPassword());
    }
}