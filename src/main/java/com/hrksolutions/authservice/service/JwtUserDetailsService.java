package com.hrksolutions.authservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hrksolutions.authservice.dto.UserDto;
import com.hrksolutions.authservice.entities.UserEntity;
import com.hrksolutions.authservice.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private static final Logger logger = Logger.getLogger(JwtUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Getting User Details by Username -"+ username);
		UserEntity user = userRepo.findByUsername(username);
		if (user == null) {
			logger.error("User not found with username");
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user));
	}


	private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
		});
		return authorities;
	}
	
	public String save(UserDto user) {
		UserEntity newUser = new UserEntity();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		userRepo.save(newUser);
		return "Success";
	}
	
	public Optional<UserEntity> findByUserId(Long id) {
		return userRepo.findById(id);
	}
	
	public Long getUserIdbyUsername(String username) {
		return userRepo.findByUsername(username).getId();
	}
}