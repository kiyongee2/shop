package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.shop.config.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class OrderItem extends BaseEntity{
	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	private int orderPrice;   //주문 가격
	
	private int count;  //주문 수량
	
	//주문 상품과 상품이 다대일 관계 - 하나의 상품이 여러 주문 상품으로 들어갈수 있음
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	//한 번에 여러개의 상품 주문, 주문 상품과 주문이 다대일 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id")
	private Orders orders;
	
	//주문할 상품과 수량으로 주문 상품(OrderItem) 객체 생성
	public static OrderItem createOrderItem(Item item, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setCount(count);
		orderItem.setOrderPrice(item.getPrice());
		
		item.removeStock(count); //주문 수량 만큼 상품의 재고 수량 감소
		return orderItem;
	}
	
	//주문 금액(주문가격 * 수량)
	public int getTotalPrice() {
		return orderPrice * count;
	}
	
}
