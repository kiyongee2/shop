package com.shop.dto;

import com.shop.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {
	
	private String searchDateType;  //등록일 기준 조건
	
	private ItemSellStatus searchSellStatus;  //판매 상태 기준 조건
	
	private String searchBy;   //검색 유형
	
	private String searchQuery = "";  //조회할 검색어
	
}
