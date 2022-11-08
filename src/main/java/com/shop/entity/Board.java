package com.shop.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.shop.config.BaseEntity;
import com.shop.dto.BoardDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter @Setter
@Entity
public class Board extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "board_id")
	private Long id;
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@Column(updatable = false, columnDefinition = "bigint DEFAULT 0")
	private Long cnt = 0L;
	
	//다대일 연관 매핑
	@ManyToOne(fetch = FetchType.LAZY)
	private Member writer;
	
	//게시글 수정
	public void updateBoard(BoardDto boardDto) {
		this.title = boardDto.getTitle();
		this.content = boardDto.getContent();
	}
}
