package com.shop.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dto.OrderDto;
import com.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OrderController {

	private final OrderService orderService;
	
	//주문하기
	@PostMapping("/order")
	public @ResponseBody ResponseEntity order(
			@RequestBody @Valid OrderDto orderDto,
			BindingResult bindingResult, Principal principal){
		//유효성 검증
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
			}
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String email = principal.getName();  //로그인한 회원
		Long orderId;  //주문 번호
		try {
			orderId = orderService.order(orderDto, email);  //주문 로직 호출
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	
		//결과값으로 생성된 주문 번호와 요청이 성공했다는 HTTP 응답 상태 코드를 반환
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
		
	}
}
