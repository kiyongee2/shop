package com.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;

//@Transactional
@SpringBootTest
public class MemberServiceTest {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder pwEncoder;
	
	//회원 생성
	public Member createMember() {
		MemberFormDto memberFormDto = new MemberFormDto();
		memberFormDto.setEmail("test3@test.com");
		memberFormDto.setName("이순신");
		memberFormDto.setAddress("서울시 구로구");
		memberFormDto.setPassword("1234");
		return Member.createMember(memberFormDto, pwEncoder);
	}
	
	//회원 가입
	@Test
	public void saveMemberTest() {
		Member member = createMember();
		memberService.saveMember(member);
	}
	
	//이메일 중복 체크 
	/*@Test
	public void saveDuplicateMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		memberService.saveMember(member1);
		
		Throwable e = assertThrows(IllegalStateException.class, () -> {
			memberService.saveMember(member2);
		});
		
		assertEquals("이미 가입된 회원입니다.", e.getMessage());
	}*/
	
	
	
	
	
}
