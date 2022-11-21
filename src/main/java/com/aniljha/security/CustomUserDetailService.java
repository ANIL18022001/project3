package com.aniljha.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aniljha.entity.User;
import com.aniljha.exception.ResourceNotFoundException;
import com.aniljha.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by username
		
	User users=this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user","email: "+username,0));
		return users;
	}

}
