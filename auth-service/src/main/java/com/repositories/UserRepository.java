package com.repositories;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.models.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
	public AppUser findByUsername(String username);

	public AppUser findByEmail(String email);

	public User save(User user);
}
