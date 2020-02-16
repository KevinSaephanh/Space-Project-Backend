package com.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class AppUser implements Serializable {
	private static final long serialVersionUID = -3617614278761535312L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank
	@Column(nullable = false, length = 25, unique = true)
	private String username;

	@Email
	@NotBlank
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@NotBlank
	@Column(nullable = false, length = 100)
	private String password;

	@NotBlank
	@Column(nullable = false, length = 10)
	private String role;

	public AppUser() {
	}

	public AppUser(int id, @NotBlank String username, @Email @NotBlank String email, @NotBlank String password, @NotBlank String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AppUser appUser = (AppUser) o;
		return id == appUser.id &&
				Objects.equals(username, appUser.username) &&
				Objects.equals(email, appUser.email) &&
				Objects.equals(password, appUser.password) &&
				Objects.equals(role, appUser.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, password, role);
	}

	@Override
	public String toString() {
		return "AppUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
