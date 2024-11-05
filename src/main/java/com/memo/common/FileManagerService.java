package com.memo.common;

import java.io.File;

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
		
		return null;
	}
}
