package services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

import dtos.UserDTO;
import models.AppUser;
import repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private BCryptPasswordEncoder encoder;

	private AuthenticationManager authenticationManager;

	private UserRepository userRepository;

	@Autowired
	public UserService(BCryptPasswordEncoder encoder, AuthenticationManager authenticationManager,
			UserRepository userRepository) {
		super();
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
	}

	public UserDTO getById(int id) {
		return userRepository.findById(id);
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

	public UserDTO login(String username, String password) throws Exception {
		authenticate(username, password);

		AppUser user = userRepository.findByUsername(username);
		encoder.matches(password, user.getPassword());
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean update(@Valid User user, int id) {
		// TODO Auto-generated method stub
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return true;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username);

		// Check if the retrieved user's username matches the input
		if (user.getUsername().equals(username)) {
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
			return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
		}
		
		// If user not found, throw exception
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}

	public boolean update(AppUser user, int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
