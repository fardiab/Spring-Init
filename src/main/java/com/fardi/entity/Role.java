package com.fardi.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_USER("USER"), ROLE_ADMIN("ADMIN");

	private final String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String getAuthority() {
		return name();
	}

}
