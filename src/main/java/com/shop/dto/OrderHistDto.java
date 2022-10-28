package com.shop.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.shop.constant.OrderStatus;
import com.shop.entity.Orders;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderHistDto { //주문 내역 서비스를 위한 dto 정의
	private Long orderId;  //주문 아이디
	private String orderDate;  //주문일
	private OrderStatus orderStatus; //주문 상태
	//주문 상품 리스트
	private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
	
	//생성자 - order 객체를 파라미터로 받아서 필드 세팅
	public OrderHistDto(Orders order) {
		this.orderId = order.getId();
		this.orderDate = 
		   order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		this.orderStatus = order.getOrderStatus();
	}
	
	//orderItemDto를 주문상품 리스트에 추가
	public void addOrderItemDto(OrderItemDto orderItemDto) {
		orderItemDtoList.add(orderItemDto);
	}
	
}
