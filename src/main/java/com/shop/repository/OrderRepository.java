package com.shop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{
	//현재 로그인한 사용자의 주문 데이터를 조회(페이지 처리)
	@Query("SELECT o FROM Orders o "
		 + "WHERE o.member.email = :email "
		 + "ORDER BY o.orderDate DESC")
	List<Orders> findOrders(@Param("email") String email, Pageable pageable);
	
	//현재 로그인한 회원의 주문 개수가 몇 개인지 조회
	@Query("SELECT COUNT(o) FROM Orders o "
		 + "WHERE o.member.email = :email")
	Long countOrder(@Param("email") String email);
}
