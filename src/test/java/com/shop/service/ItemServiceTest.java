package com.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.proxy.EntityNotFoundDelegate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;

@SpringBootTest
public class ItemServiceTest {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	ItemRepository itemRepo;
	
	@Autowired
	ItemImgRepository itemImgRepo;
	
	//가짜 이미지 파일 생성
	List<MultipartFile> createMultipartFiles(){
		List<MultipartFile> multipartFileList = 
				new ArrayList<>();
		
		for(int i=0; i<5; i++) {
			String path = "C:/shop/item/";
			String imageName = "image" + i + ".jpg";
			MockMultipartFile multipartFile = 
					new MockMultipartFile(path, imageName,
							"image/jpg", new byte[] {1,2,3,4,5});
			multipartFileList.add(multipartFile);
		}
		
		return multipartFileList;
	}
	
	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void saveItem() throws IOException {
		ItemFormDto itemFormDto = new ItemFormDto();
		itemFormDto.setItemNm("테스트 상품");
		itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
		itemFormDto.setItemDetail("테스트 상품 상세 설명입니다.");
		itemFormDto.setPrice(1000);
		itemFormDto.setStockNumber(100);
		
		List<MultipartFile> multipartFileList =
				createMultipartFiles();
		Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
		List<ItemImg> itemImgList = itemImgRepo.findByItemIdOrderByIdAsc(itemId);
		Item item = itemRepo.findById(itemId)
				.orElseThrow(EntityNotFoundException::new);
		
		assertEquals(itemFormDto.getItemNm(), item.getItemNm());
		assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
	}
	
	
	
	
	
	
	
}
