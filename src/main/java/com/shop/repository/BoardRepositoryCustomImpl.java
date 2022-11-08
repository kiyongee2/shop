package com.shop.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.dto.BoardSearchDto;
import com.shop.entity.Board;
import com.shop.entity.QBoard;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

	private JPAQueryFactory queryFactory;  //동적으로 쿼리 생성
	
	public BoardRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery){
		if(StringUtils.equals("title", searchBy)){
            return QBoard.board.title.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("content", searchBy)){
            return QBoard.board.content.like("%" + searchQuery + "%");
        }else if(StringUtils.equals("createdBy", searchBy)){
            return QBoard.board.createdBy.like("%" + searchQuery + "%");
        }

        return null;
	}
	
	//queryFactory 객체를 이용해서 쿼리를 생성함
	@Override
	public Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
		//검색한 리스트(content)
		List<Board> content = queryFactory
				.selectFrom(QBoard.board)
				.where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
				.orderBy(QBoard.board.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		//검색한 전체 개수
		long total = queryFactory.select(Wildcard.count).from(QBoard.board)
				.where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
				.fetchOne();
				
		return new PageImpl<>(content, pageable, total);
	}
}
