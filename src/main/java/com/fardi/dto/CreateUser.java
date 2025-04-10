package com.fardi.dto;

import java.util.Set;

import com.fardi.entity.Role;

import lombok.Builder;

@Builder
public record CreateUser(
		String name,
		String username,
		String password,
		Set<Role> authorities
		) {	
	
	
}
