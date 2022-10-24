package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	//이메일 가져오기(중복 체크를 위해)
	Member findByEmail(String email);
}
