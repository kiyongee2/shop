package com.shop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;

@SpringBootTest
public class ItemRepositoryTest {
	
	@Autowired
	ItemRepository itemRepo;
	
	/*@Test
	public void createItemTest() {
		Item item = new Item();
		item.setItemNm("바지");
		item.setPrice(20000);
		item.setItemDetail("바지 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now());
		item.setUpdateTime(LocalDateTime.now());
		Item savedItem = itemRepo.save(item);
		System.out.println(savedItem.toString());
	}*/
	
	//상품 10개 저장
	/*public void createItemList() {
		for(int i=1; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품 " + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명 " + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepo.save(item);
		}
	}*/
	
	/*@Test
	public void findByItemNmTest() {
		this.createItemList();
		List<Item> itemList = itemRepo.findByItemNm("테스트 상품 5");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}*/
	
	/*@Test
	public void findByPriceLessThanOrderByPriceDescTest() {
		List<Item> itemList = itemRepo.findByPriceLessThanOrderByPriceDesc(10005);
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}*/
	
	@Test
	public void createItemList2() {
		for(int i=1; i<=5; i++) {
			Item item = new Item();
			item.setItemNm("신상품" + i);
			item.setPrice(1000 + i);
			item.setItemDetail("신상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			itemRepo.save(item);
		}
		
		for(int i=6; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("신상품" + i);
			item.setPrice(1000 + i);
			item.setItemDetail("신상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
			item.setStockNumber(0);
			itemRepo.save(item);
		}
	}
	
	//Querydsl 테스트 - 상품 상세설명이 "신상품 상세"이고,
	//가격이 1003보다 크고, 상품 상태가 SELL인 상품 검색
	@Test
	public void queryDslTest() {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		QItem item = QItem.item;
		//검색 조건
		String itemDetail = "신상품 상세";
		int price = 1003;
		String itemSellStatus = "SELL";
		
		booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
		booleanBuilder.and(item.price.gt(price));
		if(StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) {
			booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
		}
		
		//페이징 정렬 객체 생성
		Pageable pageable = PageRequest.of(0, 5);
		Page<Item> itemPagingResult = 
				itemRepo.findAll(booleanBuilder, pageable);
		//전체 개수
		System.out.println("전체 개수 : " + itemPagingResult.getTotalElements());
		
		//검색 결과 상품 리스트 출력
		List<Item> resultItemList = itemPagingResult.getContent();
		
		for(Item resultItem : resultItemList) {
			System.out.println(resultItem.toString());
		}
	}
	
	
}





