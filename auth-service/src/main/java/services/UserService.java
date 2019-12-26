package services;

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

import exceptions.BadRequestException;
import exceptions.UserNotFoundException;
import models.AppUser;
import repositories.UserRepository;

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
	public AppUser getById(int id) {
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
	public boolean update(AppUser user, int id) {
		// If id is invalid, throw exception
		if (id <= 0)
			throw new BadRequestException("Invalid id value provided");

		Optional<AppUser> retrievedUser = userRepository.findById(id);

		// If user is not found, throw exception
		if (!retrievedUser.isPresent())
			throw new UserNotFoundException("No user found with id: " + id);

		userRepository.save(user);
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
}
