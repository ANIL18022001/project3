package com.aniljha.services;


import java.util.List;

import com.aniljha.entity.Post;
import com.aniljha.payloads.PostDto;
import com.aniljha.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//update
	PostDto updatePost (PostDto postDto,Integer postId);
	//delete
	void deletePost(Integer postId);
	//get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	//get single post
	PostDto getPostById(Integer postId);
	
	//get all posts by category
	List<PostDto>getPostByCategory(Integer categoryId);
	//get all post by user
	List<PostDto>getPostByUser(Integer userId);
	//search post
	List<PostDto>searchPosts(String keyword);
}
