package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);

        Board saveBoard = boardRepository.save(board);  // boardRepository 사용

        BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard);

        return boardResponseDto;
    }

    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id=" + id));
        return new BoardResponseDto(board);
    }


    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, String password, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다. id=" + id)
        );

        if (!board.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    public Long deleteBoard(Long id, String password) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다. id=" + id)
        );

        if (!board.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        boardRepository.delete(board);
        return id;
    }


}