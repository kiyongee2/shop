package com.shop.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemFormDto {
	
	private Long id;
	
	@NotBlank(message = "상품명은 필수 입력 값입니다.")
	private String itemNm;   //상품명
	
	@NotNull(message = "가격은 필수 입력 값입니다.")
	private Integer price;
	
	@NotBlank(message = "상세 설명은 필수 입력 값입니다.")
	private String itemDetail;  //상세 설명
	
	@NotNull(message = "재고는 필수 입력 값입니다.")
	private Integer stockNumber;  //재고 수량
	
	private ItemSellStatus itemSellStatus; //판매 상태
	
	//상품 이미지 리스트
	private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
	
	//상품 이미지 아이디 리스트
	private List<Long> itemImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//(dto -> entity)
	public Item createItem() {
		return modelMapper.map(this, Item.class);
	}
	
	//entity -> dto
	public static ItemFormDto of(Item item) {
		return modelMapper.map(item, ItemFormDto.class);
	}
	
}
