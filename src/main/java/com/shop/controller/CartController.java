package com.shop.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.dto.OrderDto;
import com.shop.service.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CartController {
	
	private final CartService cartService;
	
	//장바구니 담기
	@PostMapping("/cart")
	public @ResponseBody ResponseEntity<?> order(
			@RequestBody @Valid CartItemDto cartItemDto,
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
		
		String email = principal.getName();
		Long cartItemId;
		
		try {
			cartItemId = cartService.addCart(cartItemDto, email);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
	}
	
	//장바구니 목록(페이지) 보기
	@GetMapping("/cart")
	public String cartList(Principal principal, Model model) {
		List<CartDetailDto> cartDetailList = 
				cartService.getCartList(principal.getName());
		model.addAttribute("cartItems", cartDetailList);
		return "cart/cartList";
	}
	
	
	
	
	
	
}










