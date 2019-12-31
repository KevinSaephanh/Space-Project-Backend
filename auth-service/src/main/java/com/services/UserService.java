package com.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exceptions.BadRequestException;
import com.exceptions.EmailAlreadyExistsException;
import com.exceptions.UserNotFoundException;
import com.exceptions.UsernameAlreadyExistsException;
import com.models.AppUser;
import com.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private BCryptPasswordEncoder encoder;

	private UserRepository userRepository;

	@Autowired
	public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository) {
		super();
		this.encoder = encoder;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public AppUser findById(int id) {
		// If invalid id is provided, throw exception
		if (id <= 0)
			throw new BadRequestException("Invalid id value provided");

		Optional<AppUser> user = userRepository.findById(id);

		// If user with id does not exist, throw exception
		if (!user.isPresent())
			throw new UserNotFoundException("No user found with id: " + id);
		return user.get();
	}

	@Transactional(readOnly = true)
	public AppUser findByUsername(String username) {
		// Check for null or empty username
		if (username == null || username.equals(""))
			throw new BadRequestException("Invalid username provided");

		AppUser user = userRepository.findByUsername(username);

		// If user is null, throw exception
		if (user == null)
			throw new UserNotFoundException("No user found with username: " + username);

		return user;
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
	public Page<AppUser> getPage(Pageable page) {
		return userRepository.findAll(page);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean create(AppUser user) {
		// Encrypt password and save new user
		String encryptedPassword = encoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		user = userRepository.save(user);

		return user != null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean update(AppUser updatedUser, AppUser requester) {
		// Check for null users
		if (requester == null || updatedUser == null || updatedUser.getId() <= 0 || requester.getId() <= 0)
			throw new BadRequestException("Invalid user provided");

		// Check if both users have matching id's
		if (!(requester.getId() == updatedUser.getId()))
			throw new BadRequestException("Users' ids do not match");

		AppUser preUpdatedUser = findById(updatedUser.getId());

		// If user is not found, throw exception
		if (preUpdatedUser == null)
			throw new UserNotFoundException("No user found with matching id: " + updatedUser.getId());

		// Check for existing username
		String persistedUsername = preUpdatedUser.getUsername();
		String updatedUsername = updatedUser.getUsername();
		if (!persistedUsername.equals(updatedUsername)) {
			AppUser user = userRepository.findByUsername(updatedUsername);
			if (user != null)
				throw new UsernameAlreadyExistsException(updatedUsername + " is already in use");
		}

		// Check for existing email
		String persistedEmail = preUpdatedUser.getEmail();
		String updatedEmail = updatedUser.getEmail();
		if (!persistedEmail.equals(updatedEmail)) {
			AppUser user = userRepository.findByEmail(updatedEmail);
			if (user != null)
				throw new EmailAlreadyExistsException(updatedEmail + " is already in use");
		}

		userRepository.save(updatedUser);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean delete(int id) {
		// If id is invalid, throw exception
		if (id <= 0)
			throw new BadRequestException("Invalid id value provided");

		Optional<AppUser> user = userRepository.findById(id);

		// If user is not found, throw exception
		if (!user.isPresent())
			throw new UserNotFoundException("No user found with id: " + id);

		userRepository.delete(user.get());
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username);

		// Check if the retrieved user has matching username
		if (user.getUsername().equals(username)) {
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
			return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
		}

		// If user not found, throw exception
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}

	@Transactional(readOnly = true)
	private boolean isUsernameAvailable(String username) {
		// Check for empty username input
		if (username == null || username.equals(""))
			throw new BadRequestException("Invalid username provided");

		AppUser user = userRepository.findByUsername(username);

		if (user == null)
			return true;
		return false;
	}

	@Transactional(readOnly = true)
	private boolean isEmailAvailable(String email) {
		// Check for empty email input
		if (email == null || email.equals(""))
			throw new BadRequestException("Invalid email provided");

		AppUser user = userRepository.findByEmail(email);

		if (user == null)
			return true;
		return false;
	}
}
