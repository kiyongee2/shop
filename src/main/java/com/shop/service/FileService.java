package com.shop.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Log
@Service
public class FileService {
	
	//파일 업로드 메서드
	public String uploadFile(String uploadPath, String originalFileName,
			byte[] fileData) throws IOException {
		//k.abcd.jpg
		UUID uuid = UUID.randomUUID(); //유일한 파일 이름 부여
		String extension = originalFileName.substring(
				originalFileName.lastIndexOf(".")); //확장자
		String savedFileName = uuid.toString() + extension;  //파일 이름을 만듬
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		//바이트 단위로 파일 쓰기
		//파일 출력 스트림을 생성함(생성자로 파일이 저장될 위치와 파일 이름을 넘겨줌)
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData);
		fos.close();
		
		return savedFileName;
	}

	//파일 삭제
	public void deleteFile(String filePath) {
		//파일이 저장된 경로를 이용하여 파일 객체를 생성함
		File deleteFile = new File(filePath);
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("파일을 삭제하였습니다.");
		}else {
			log.info("파일이 존재하지 않습니다.");
		}
	}

}
