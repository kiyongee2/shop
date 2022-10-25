package com.shop.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemFormDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemRepository itemRepo;
	private final ItemImgRepository itemImgRepo;
	private final ItemImgService itemImgService;
	
	//상품 저장(상품, 이미지)
	public Long saveItem(ItemFormDto itemFormDto, 
			List<MultipartFile> itemImgFileList) throws IOException {
		//상품 등록
		Item item = itemFormDto.createItem();
		itemRepo.save(item);
		
		//이미지 등록
		for(int i=0; i<itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			if(i == 0) {
				itemImg.setRepimgYn("Y");
			}else {
				itemImg.setRepimgYn("N");
			}
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		return item.getId();
	}
}








