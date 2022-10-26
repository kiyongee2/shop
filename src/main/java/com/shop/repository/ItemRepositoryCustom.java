package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;

public interface ItemRepositoryCustom {

	//상품 조회 조건을 담고 있는 itemSearchDto 객체와 
	//페이징 정보를 담고 있는 pageable 객체를 파라미터로 함
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto,
			Pageable pageable);
}
