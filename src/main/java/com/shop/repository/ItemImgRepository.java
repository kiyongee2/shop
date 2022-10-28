package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{
	//상품의 이미지 정보를 id로 오름차순 정렬하여 조회
	List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
	
	//상품의 대표 이미지 조회
	ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
}
