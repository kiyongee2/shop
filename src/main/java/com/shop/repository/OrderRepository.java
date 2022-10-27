package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{

}
