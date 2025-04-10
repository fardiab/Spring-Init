package com.fardi.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fardi.dto.CreateUser;
import com.fardi.entity.User;
import com.fardi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	
	public Optional<User> getByUserName(String username) {
		return userRepository.getByUserName(username);
	}
	
	
	public User createUser(CreateUser request) {
		User newUser = User.builder()
				.name(request.name())
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.accountNonLocked(true)
				.isEnabled(true)
				.accountNonExpired(true)
				.credentialsNonExpired(true)
				.authorities(request.authorities())
				.build();
		return userRepository.save(newUser);
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = getByUserName(username);
		return user.orElseThrow(EntityNotFoundException::new);
	}
}
