package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.BoardDto;
import com.shop.dto.BoardSearchDto;
import com.shop.entity.Board;
import com.shop.entity.Member;
import com.shop.repository.BoardRepository;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepo;
	private final MemberRepository memberRepo;
	
	//글쓰기
	public void register(BoardDto boardDto) {
		Board board = boardDto.createBoard();
		boardRepo.save(board);
		
		//return board.getId();
	}
	
	//게시글 목록 보기(검색, 페이징)
	public Page<Board> getBoardPage(BoardSearchDto boardSearchDto, 
			Pageable pageable){
		return boardRepo.getBoardPage(boardSearchDto, pageable);
	}
	
	//글 상세보기
	public BoardDto getBoardDtl(Long id) {
		Board board = boardRepo.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		BoardDto boardDto = BoardDto.of(board);
		return boardDto;
				
	}
	
	//글 수정하기
	public void update(BoardDto dto) {
		//전달받은 상품 아이디로 상품 엔티티 조회
		Board board = boardRepo.findById(dto.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		board.updateBoard(dto); //updateBoard() 호출
	}
	
	//글 삭제하기
	public void delete(Long id) {
		Board board = boardRepo.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		boardRepo.delete(board);
	}
	
	//조회수 증가
	@Transactional
	public void updateCount(Long id) {
		boardRepo.updateCount(id);
	}
}
