package com.shop.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartOrderDto {

	private Long cartItemId;  //품목 아이디
	
	private List<CartOrderDto> cartOrderDtoList; //품목 리스트
}
