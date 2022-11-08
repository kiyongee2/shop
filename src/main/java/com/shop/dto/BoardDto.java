package com.shop.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.shop.entity.Board;
import com.shop.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDto {
	
	private Long id;
	
	@NotEmpty(message = "제목을 입력해주세요")
	private String title;
	
	@NotEmpty(message = "내용을 입력해주세요")
	private String content;
	
	private String createdBy;  //등록자(이메일)
	
	private LocalDateTime regTime;
	
	private Long cnt = 0L;
	
	//dto<->entity 변환
	private static ModelMapper modelMapper = new ModelMapper();
	
	//entity로 변환
	public Board createBoard() {
		return modelMapper.map(this, Board.class);
	}
	
	//dto로 변환
	public static BoardDto of(Board board) {
		return modelMapper.map(board, BoardDto.class);
	}
	
}




