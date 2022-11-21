package com.aniljha.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aniljha.entity.Category;
import com.aniljha.entity.Post;
import com.aniljha.entity.User;
import com.aniljha.exception.ResourceNotFoundException;
import com.aniljha.payloads.CategoryDto;
import com.aniljha.payloads.PostDto;
import com.aniljha.payloads.PostResponse;
import com.aniljha.repository.CategoryRepo;
import com.aniljha.repository.PostRepo;
import com.aniljha.repository.UserRepo;
import com.aniljha.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatePost=this.postRepo.save(post);
		
		return modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {

		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id", postId));
		  this.postRepo.delete(post);
	}

	//get all post
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
//		Integer pagenumber=1;
//		Integer pagesize=5;

		Sort sort=(sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}else {
//			sort=Sort.by(sortBy).descending();
//		}
		Pageable p= PageRequest.of(pageNumber, pageSize,sort);
		
//		Pageable p= PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post>allPosts=pagePost.getContent();
//		List<Post>posts=this.postRepo.findAll();
//		List<PostDto>postDtos=posts.stream().map(post->this.PostToDto(post)).collect(Collectors.toList());
		List<PostDto>postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements((int) pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}
		
	@Override
	public PostDto getPostById(Integer postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id", postId));
		
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
		List<Post>posts=this.postRepo.findByUser(user);
		
		List<PostDto>postDto=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);// if we are using query  then use("%"+keyword+"%")
		List<PostDto> postDto = posts.stream().map((post)->this.modelMapper
				.map(post,PostDto.class)).collect(Collectors.toList());
		return postDto;
	}
//	
//	/// model paper
//	public Post dtoToPost(PostDto postDto) {
//		Post post = this.modelMapper.map(postDto, Post.class);
//		return post;
//
//	}
//
//	public PostDto postToDto(Post post) {
//		PostDto postDto = this.modelMapper.map(post, PostDto.class);
//		return postDto;
//	}

}
