package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {
	private final ItemRepository itemRepo;
	private final MemberRepository memberRepo;
	private final CartRepository cartRepo;
	private final CartItemRepository cartItemRepo;
	
	//장바구니 생성(담기)
	public Long addCart(CartItemDto cartItemDto, String email) {
		//상품 조회 및 회원 조회
		Item item = itemRepo.findById(cartItemDto.getItemId())
				.orElseThrow(EntityNotFoundException::new);
		Member member = memberRepo.findByEmail(email);
		
		//장바구니 엔티티 조회
		Cart cart = cartRepo.findByMemberId(member.getId());
		
		//장바구니 생성
		if(cart == null) {
			cart = Cart.createCart(member);
			cartRepo.save(cart);
		}
		
		//장바구니 품목 생성
		CartItem savedCartItem = 
				cartItemRepo.findByCartIdAndItemId(cart.getId(), item.getId());
		
		if(savedCartItem != null) { //장바구니에 이미 담긴 상품은 수량 추가
			savedCartItem.addCount(cartItemDto.getCount());
			return savedCartItem.getId();
		}else {//savedCartItem == null
			CartItem cartItem =
					CartItem.createCartItem(cart, item, cartItemDto.getCount());
			cartItemRepo.save(cartItem);
			return cartItem.getId();
		}
	}//addCart() 끝
	
	
	//장바구니 목록 보기
	@Transactional(readOnly = true)
	public List<CartDetailDto> getCartList(String email){
		List<CartDetailDto> cartDetailDtoList = new ArrayList<>();
		Member member = memberRepo.findByEmail(email);
		
		Cart cart = cartRepo.findByMemberId(member.getId());
		
		if(cart == null) { //장바구니가 없으면
			return cartDetailDtoList;
		}
		
		//장바구니가 있으면
		cartDetailDtoList = cartItemRepo.findCartDetailDtoList(cart.getId());
		return cartDetailDtoList;
	}
}








