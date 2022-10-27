package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.OrderDto;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.OrderItem;
import com.shop.entity.Orders;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {
	
	private final ItemRepository itemRepo;
	private final MemberRepository memberRepo;
	private final OrderRepository orderRepo;
	
	//주문하기
	public Long order(OrderDto orderDto, String email) {
		//주문할 상품 조회
		Item item = itemRepo.findById(orderDto.getItemId())
				.orElseThrow(EntityNotFoundException::new);
		
		//로그인한 회원 가져오기
		Member member = memberRepo.findByEmail(email);
		
		//주문할 상품 리스트를 이용
		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
		orderItemList.add(orderItem);
		
		//주문 엔티티 생성
		Orders order = Orders.createOrder(member, orderItemList);
		orderRepo.save(order);
		
		return order.getId();
	}
	
	
	
	
	
	
	
	
	
	
	
}
