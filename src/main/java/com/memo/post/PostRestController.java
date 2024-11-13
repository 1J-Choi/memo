package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {
	private final PostBO postBO;
	
	/**
	 * 글쓰기 API
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		// session에서 userId, userLoginId 얻어오기
		int userId = (int) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		// db insert
		int rowCount = postBO.addPost(userId, userLoginId, subject, content, file);

		// return result
		Map<String, Object> result = new HashMap<>();
		if(rowCount > 0) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "글이 저장되지 않았습니다.");
		}
		return result;
	}
	
	/**
	 * 글 수정
	 * @param postId
	 * @param content
	 * @param subject
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("content") String content,
			@RequestParam("subject") String subject,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		// session에서 userId(db), userLoginId(파일업로드) 가져오기
		int userId = (int) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		// db update + 파일 업로드
		postBO.updatePostByPostIdUserId(userId, userLoginId, postId, content, subject, file);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	/**
	 * 글 삭제
	 * @param postId
	 * @param session
	 * @return
	 */
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session) {
		// session에서 userId 가져오기
		int userId = (int) session.getAttribute("userId");
		
		// db delete + 파일 삭제
		postBO.deletePostByPostIdAndUserId(postId, userId);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
