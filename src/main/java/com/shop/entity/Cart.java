package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.shop.config.BaseEntity;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter @Setter
@Entity
public class Cart extends BaseEntity{
	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//회원 1명당 1개의 장바구니를 가짐 - 일대일 관계
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	//카트 생성
	public static Cart createCart(Member member) {
		Cart cart = new Cart();
		cart.setMember(member);
		return cart;
	}
}




