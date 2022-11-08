package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shop.dto.BoardSearchDto;
import com.shop.entity.Board;

//사용자 정의 인터페이스 작성
public interface BoardRepositoryCustom {
	
	//상품 조회 조건을 담고 있는 itemSearchDto 객체와 페이징 정보를 담고 있는 pageable 객체를 파라미터로 함
	Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable);
	
}
