package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // Spring bean에 올려야하는데 애매할 때 쓰는 어노테이션
public class FileManagerService {
	// 실제 업로드 된 이미지가 저장될 서버 경로
	public static final String FILE_UPLOAD_PATH = "C:\\Users\\최원제\\Desktop\\WJ\\공부\\코딩\\백엔드\\학원\\신보람\\6_spring_project\\memo\\memo_workspace\\images/";
	
	// input: MultipartFile, userLoginId
	// output: String(이미지 경로)
	public String uploadFile(MultipartFile file, String loginId) {
		// 폴더(디렉토리) 생성
		// ex: aaaa_17237482334/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis(); // aaaa_17237482334
		// C:\\Users\\최원제\\Desktop\\WJ\\공부\\코딩\\백엔드\\학원\\신보람\\6_spring_project\\memo\\memo_workspace\\images/aaaa_17237482334/
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		// 폴더 생성
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null; // 폴더 생성시 실패하면 경로를 null로 리턴(에러 아님)
		}
		
		// 파일 업로드 (오류가 발생하더라도 null이 BO에게 반환되어야 한다)
		try {
			byte[] bytes = file.getBytes();
			// ★★★★★ 나중에 파일명을 영문자로 변경할 것!(한글명은 업로드 불가) ★★★★★
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 시 실패하면 null로 리턴(에러 아님)
		}
		
		// 파일 업로드가 성공하면 이미지 url path 리턴
		// 주소는 이렇게 될 것이다.(예언)
		// 		/images/aaaa_17237482334/sun.png
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
}
