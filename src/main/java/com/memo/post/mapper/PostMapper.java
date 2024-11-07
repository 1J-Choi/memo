package com.memo.post.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.post.domain.Post;

@Mapper
public interface PostMapper {
	// input: X
	// output: List<Map<String, Object>>
	public List<Map<String, Object>> selectPostList();
	// input: userId
	// output: List<Post>
	public List<Post> selectPostListByUserId(int userId);
	
	public int insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	public Post selectPostBypostIdAndUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
}
