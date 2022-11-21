package com.aniljha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aniljha.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
