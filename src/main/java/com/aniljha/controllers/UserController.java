package com.aniljha.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aniljha.payloads.ApiResponse;
import com.aniljha.payloads.UserDto;
import com.aniljha.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	//post-create user
	@PostMapping("/")
	public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto=this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//put- update user
	 @PutMapping("/{userId}")
	 public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId")Integer uid){
		 
		 UserDto updateuser=this.userService.updateUser(userDto, uid);
		 return  ResponseEntity.ok(updateuser);
	 }
	//delete- daelete user
	 @DeleteMapping("/{userId}")
	 public ResponseEntity<ApiResponse>deleteUser(@PathVariable("userId")Integer uid){
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
	 }
	
	//Get -user get
	 @GetMapping("/{userId}")
	 public ResponseEntity<UserDto>getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId)); 
	 }
	 
	 //getalll
	 @GetMapping("/")
	 public ResponseEntity<List<UserDto>>getAlluser(@RequestBody UserDto userDto){
		 return ResponseEntity.ok(this.userService.getAllUsers());
	 }
	 
}
