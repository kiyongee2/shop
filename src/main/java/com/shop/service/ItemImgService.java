package com.shop.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Transactional
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
	
	//이미지 수정 메서드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws IOException {
		
		if(!itemImgFile.isEmpty()) {
			//상품 이미지 아이디로 이미지 엔티티를 조회
			ItemImg savedItemImg = itemImgRepo.findById(itemImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일을 삭제
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
				fileService.deleteFile(itemImgLocation + "/" + 
								savedItemImg.getImgName());
			}
			
			//수정한 이미지 파일 업로드
			String oriImgName = itemImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(itemImgLocation, 
					oriImgName, itemImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
		}
	}
	
	
}
