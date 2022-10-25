package com.shop.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemImgService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
	private final ItemImgRepository itemImgRepo;
	
	private final FileService fileService;
	
	//이미지 저장 메서드
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
		String oriImgName = itemImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, 
					itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		//상품 이미지 정보
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		itemImgRepo.save(itemImg);  //이미지 정보 및 파일 저장
	}
	
}
