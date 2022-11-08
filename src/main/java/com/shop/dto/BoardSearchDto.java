package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardSearchDto {
	
	private String searchDateType;   //현재 시간과 등록일을 비교해서 상품 조회
	
	private String searchBy;  //상품 조회시 어떤 유형으로 조회할지 선택
	
	private String searchQuery = "";  //조회할 검색어 저장할 변수
}
