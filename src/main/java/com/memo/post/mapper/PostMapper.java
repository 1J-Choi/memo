package com.memo.post.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.memo.post.domain.Post;

@Mapper
public interface PostMapper {
	// input: X
	// output: List<Map<String, Object>>
	public List<Map<String, Object>> selectPostList();
	// input: userId
	// output: List<Post>
	public List<Post> selectPostListByUserId(int userId);
}
