package com.dtos;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.models.AppUser;

public class UserPrincipal extends User {
	private static final long serialVersionUID = 1L;

	private final AppUser appUser;

	public UserPrincipal(AppUser appUser, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.appUser = appUser;
	}

	public AppUser getAppUser() {
		return appUser;
	}
}
