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
	public List<Post> selectPostListByUserId(
			@Param("userId") int userId, 
			@Param("standardId") Integer standardId, 
			@Param("direction") String direction, 
			@Param("limit") int limit);
	
	public int selectPostIdByUserIdAsSort(
			@Param("userId") int userId, 
			@Param("sort") String sort);
	
	public int insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	public Post selectPostByPostIdAndUserId(
			@Param("postId") int postId, 
			@Param("userId") int userId);
	public void updatePostByPostId(
			@Param("postId") int postId,
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	public int deletePost(int postId);
}
