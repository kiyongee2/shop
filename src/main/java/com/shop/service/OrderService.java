package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.dto.OrderItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.entity.Member;
import com.shop.entity.OrderItem;
import com.shop.entity.Orders;
import com.shop.repository.ItemImgRepository;
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
	private final ItemImgRepository itemImgRepo;
	
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
	
	//주문 내역
	public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
		//주문 조회
		List<Orders> orders = orderRepo.findOrders(email, pageable);
		//주문 총개수
		Long totalCount = orderRepo.countOrder(email);
		
		//주문 리스트를 순회하면서 주문 내역 페이지에 전달할 dto 생성
		List<OrderHistDto> orderHistDtos = new ArrayList<>();
		for(Orders order : orders) {
			OrderHistDto orderHistDto = new OrderHistDto(order);
			List<OrderItem> orderItems = order.getOrderItems();
			for(OrderItem orderItem : orderItems) {
				//대표 이미지 조회
				ItemImg itemImg = 
						itemImgRepo.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
				
				OrderItemDto orderItemDto =
						new OrderItemDto(orderItem, itemImg.getImgUrl());
				orderHistDto.addOrderItemDto(orderItemDto);  //객체 1개 생성		
			}//안쪽 for() 끝
			orderHistDtos.add(orderHistDto);
		}//바깥쪽 for() 끝
		
		return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
	}
	
	
	
	
	
	
	
	
	
}
