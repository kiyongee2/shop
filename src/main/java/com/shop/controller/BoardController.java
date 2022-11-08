package com.shop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.BoardDto;
import com.shop.dto.BoardSearchDto;
import com.shop.entity.Board;
import com.shop.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

	private final BoardService boardService;
	
	//게시글 목록 보기
	/*@GetMapping("/list")
	public String list(Model model, BoardDto boardDto) {
		List<BoardDto> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		model.addAttribute("dto", new BoardDto());
		return "board/list";
	}*/
	
	@GetMapping({"/list", "/list/{page}"})
	public String list(BoardSearchDto boardSearchDto,
			@PathVariable("page") Optional<Integer> page, 
			Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
		Page<Board> boards = boardService.getBoardPage(boardSearchDto, pageable);
		model.addAttribute("boards", boards);
		model.addAttribute("boardSearchDto", boardSearchDto);
		model.addAttribute("maxPage", 5);
		return "board/list";
	}
	
	//글쓰기 페이지 요청
	@GetMapping("/register")
	public String register(Model model) {
		BoardDto boardDto = new BoardDto();
		model.addAttribute("boardDto", boardDto);
		return "board/register";
	}
	
	//글쓰기 처리
	@PostMapping("/register")
	public String register(@Valid BoardDto boardDto, 
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "board/register";
		}
		try {
			boardService.register(boardDto);
		} catch (Exception e){
			model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생하였습니다.");
			return "board/register";
		}
		return "redirect:list";
	}
	
	//글 상세보기
	@GetMapping("/read/{boardId}")
	public String boardDtl(@PathVariable("boardId") Long boardId, Model model) {
		//조회수 증가
		boardService.updateCount(boardId);
		
		//게시글 조회
		BoardDto boardDto = boardService.getBoardDtl(boardId);
		model.addAttribute("boardDto", boardDto);
		return "board/read";
	}
	
	//글 수정하기
	@PostMapping("/update")
	public String updateBoard(@Valid BoardDto boardDto,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "board/read";
		}
		boardService.update(boardDto);
		return "redirect:list";
	}
	
	//글 삭제하기
	@GetMapping("/delete")
	public String deleteBoard(Long id) {
		boardService.delete(id);
		return "redirect:list";
	}
}
