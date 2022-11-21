package com.aniljha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aniljha.payloads.ApiResponse;
import com.aniljha.payloads.CommentDto;
import com.aniljha.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentservice;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		CommentDto commentdto = this.commentservice.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(commentdto,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/coments/{commentId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId){
		this.commentservice.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully !!",true),HttpStatus.CREATED);
	}
	
	
}
