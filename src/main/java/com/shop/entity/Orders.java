package com.shop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.shop.config.BaseEntity;
import com.shop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Orders extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orders_id")
	private Long id;
	
	//주문과 회원은 다대일 관계
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	private LocalDateTime orderDate;  //주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;  //주문 상태
	
	//양방향 연관 매핑의 주인 설정(OrderItem이 주인)
	//영속성 전이(cascade) - Orders를 삭제하면 OrderItem도 함께 삭제됨
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	//주문 상품 추가
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrders(this);  //Orders  엔티티를 orderItem에 세팅
	}
	
	//주문 추가
	public static Orders createOrder(Member member, 
			List<OrderItem> orderItemList) {
		Orders order = new Orders();
		order.setMember(member);  //상품을 주문한 회원 세팅
		
		//여러개 상품 주문
		for(OrderItem orderItem : orderItemList) {
			order.addOrderItem(orderItem); 
		}
		
		order.setOrderStatus(OrderStatus.ORDER);  //주문 상태
		order.setOrderDate(LocalDateTime.now());  //주문 시간
		return order;
	}
	
	//총 주문 금액 계산(누적)
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
}





