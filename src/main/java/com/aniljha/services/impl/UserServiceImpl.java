package com.aniljha.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aniljha.entity.User;
import com.aniljha.exception.ResourceNotFoundException;
import com.aniljha.payloads.UserDto;
import com.aniljha.repository.UserRepo;
import com.aniljha.services.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user =this.dtoToUser(userDto);
		User savedUser =this.userRepo.save(user);		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId)
						
						
						);
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updateuser=this.userRepo.save(user);
		UserDto userDto1=this.userToDto(updateuser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
        
		User user =this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>users=this.userRepo.findAll();
		List<UserDto>userDtos=users.stream()
				.map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);
	}

//	public User dtoToUser(UserDto userDto)
//	{
//		User user=new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		return user;
//	}
//	public UserDto userToDto(User user)
//	{
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		return userDto;
//	}
	public User dtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto,User.class);
		return user;
		
	}

	public UserDto userToDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
		return userDto;
	}
}
