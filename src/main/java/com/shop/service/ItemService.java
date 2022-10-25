package com.shop.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemRepository itemRepo;
	private final ItemImgService itemImgService;
	private final ItemImgRepository itemImgRepo;
	
	//상품 저장(상품, 이미지)
	public Long saveItem(ItemFormDto itemFormDto, 
			List<MultipartFile> itemImgFileList) throws IOException {
		//상품 등록
		Item item = itemFormDto.createItem();
		itemRepo.save(item);
		
		//이미지 등록
		for(int i=0; i<itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item); //item 객체를 참조
			
			if(i == 0) { //첫번째 이미지일 경우 대표 상품 이미지 값을 "Y"로 세팅
				itemImg.setRepimgYn("Y");
			}else {
				itemImg.setRepimgYn("N");
			}
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		return item.getId();
	}
	
	//상품 상세 보기(entity -> dto)
	@Transactional(readOnly = true)
	public ItemFormDto getItemDtl(Long itemId) {
		//해당 상품의 이미지 조회(id 오름차순)
		List<ItemImg> itemImgList = 
				itemImgRepo.findByItemIdOrderByIdAsc(itemId);
		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
		//조회한 ItemImg 엔티티를 ItemImgDto로 만들어서 리스트에 추가함
		for(ItemImg itemImg : itemImgList) {
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
			itemImgDtoList.add(itemImgDto);
		}
		
		//상품의 id를 통해 상품 엔티티를 조회
		//존재하지 않을 때는 EntityNotFoundException 발생
		Item item = itemRepo.findById(itemId)
				.orElseThrow(EntityNotFoundException::new);
		
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		return itemFormDto;	
	}
}








