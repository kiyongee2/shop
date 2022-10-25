package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shop.dto.ItemFormDto;

@Controller
public class ItemController {
	
	//상품 등록 페이지 요청
	@GetMapping("/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "item/itemForm";
	}
	
}
