package com.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.CartItemDto;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@SpringBootTest
public class CartServiceTest {
	@Autowired
	ItemRepository itemRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	@Autowired
	CartService cartService;
	
	//상품 저장
	public Item saveItem() {
		Item item = new Item();
		item.setItemNm("test 상품");
		item.setPrice(10000);
		item.setItemDetail("test 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		return itemRepo.save(item);
	}
		
	//회원 저장
	public Member saveMember() {
		Member member = new Member();
		member.setEmail("tester@test.com");
		return memberRepo.save(member);
	}
	
	@Test
	public void addCartTest() {
		Item item = saveItem();
		Member member = saveMember();
		
		CartItemDto cartItemDto = new CartItemDto();
		cartItemDto.setItemId(item.getId());
		cartItemDto.setCount(10);
		
		Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());
		CartItem cartItem = cartItemRepo.findById(cartItemId)
				.orElseThrow(EntityNotFoundException::new);
		
		//테스트 통과(상품 아이디, 장바구니에 담긴 상품 아이디)
		assertEquals(item.getId(), cartItem.getItem().getId());
		//수량 테스트(장바구니 담은 수량, 실제 장바구니에 저장된 수량)
		assertEquals(cartItemDto.getCount(), cartItem.getCount());
	}
}








