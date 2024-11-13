package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j // slf4j logger, loombok에서 제공
public class PostBO {
	// slf4j 쪽 것으로 import 하기
	// private Logger log = LoggerFactory.getLogger(PostBO.class);
	// private Logger log = LoggerFactory.getLogger(this.getClass());
	// 어노테이션 붙이면 log가 자동으로 생김
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private FileManagerService fileManager;
	
	public List<Post> getPostListByUserId(int userId){
		return postMapper.selectPostListByUserId(userId);
	}
	// input: userId, userLoginId, subject, content, file
	// output: int(성공한 행의 갯수)
	public int addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		String imagePath = null;
		
		// file to imagePath
		// file이 있을 때만 업로드 => imagePath를 얻어냄
		if(file != null) {
			imagePath = fileManager.uploadFile(file, userLoginId);
		}
		
		return postMapper.insertPost(userId , subject, content, imagePath);
	}
	
	public Post getPostBypostIdAndUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdAndUserId(postId, userId);
	}
	
	// input: userId, userLoginId, subject, content, file
	// output: x
	public void updatePostByPostIdUserId(int userId, String userLoginId, int postId, 
			String content, String subject, MultipartFile file) {
		// 기존 글을 가져온다.(1.이미지 교체시 기존 이미지 삭제를 위해서, 2.업데이트 대상 존재 검증)
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if (post == null) {
			log.info("[#####글 수정#####] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		log.info("[#####글 수정#####] postId:{}, userId:{}", postId, userId);
		
		// 파일 존재 시 이미지 업로드
		
		// 기존 글에 이미지가 없다
		// 파일 업로드 => 성공 DB 저장 O
		// 			   => 실패 DB 저장 X
		
		// 기존 글에 이미지가 있다
		// 파일 업로드 => 성공 기존 이미지 삭제 후 DB 저장 O
		// 			   => 실패 기존 이미지 그대로, DB 저장 X
		String imagePath = null;
		if(file != null) {
			// 새 이미지 업로드
			imagePath = fileManager.uploadFile(file, userLoginId);
			// 업로드 성공 시 기존 이미지 존재하면 삭제
			if(imagePath != null && post.getImagePath() != null) {
				// 폴더, 이미지 제거(컴퓨터-서버)
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		// DB update
		// xml에서 분기 하기
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}
	
	public void deletePostByPostIdAndUserId(int postId, int userId) {
		// 삭제할 글 가져오기 (없으면 바로 return) => 이미지 존재 시 삭제 위해서
		Post post = postMapper.selectPostByPostIdAndUserId(postId, userId);
		if (post == null) {
			log.info("[#####글 삭제#####] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		log.info("[#####글 삭제#####] postId:{}, userId:{}", postId, userId);
		
		// DB 삭제
		int rowCount = postMapper.deletePost(postId);
		
		// db 행 삭제 완료 && post에 imagePath가 있을 시 이미지 파일과 폴더 제거
		if(rowCount > 0 && post.getImagePath() != null) {
				fileManager.deleteFile(post.getImagePath());
		}
		
	}
}
