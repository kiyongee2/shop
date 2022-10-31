package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import com.shop.config.BaseEntity;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter @Setter
@Entity
public class CartItem extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cart_item_id")
	private Long id;
	
	private int count;  //같은 상품을 여러개 주문
	
	//하나의 상품은 여러 장바구니에 담길수 있으므로 다대일 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	//하나의 장바구니에 여러 개의 상품이 담김(다대일 관계)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	//장바구니 품목 생성
	public static CartItem createCartItem(Cart cart, Item item, int count) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setItem(item);
		cartItem.setCount(count);
		return cartItem;
	}
	
	//해당 상품을 추가로 장바구니에 담음
	public void addCount(int count) {
		this.count += count;
	}
}



