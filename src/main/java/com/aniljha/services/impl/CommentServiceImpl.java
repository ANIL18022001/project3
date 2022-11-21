package com.aniljha.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aniljha.entity.Comment;
import com.aniljha.entity.Post;
import com.aniljha.exception.ResourceNotFoundException;
import com.aniljha.payloads.CommentDto;
import com.aniljha.repository.CommentRepo;
import com.aniljha.repository.PostRepo;
import com.aniljha.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postrepo;
	
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postrepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comdelete = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "CommentId",commentId));
		this.commentRepo.delete(comdelete);
		
	}

}
