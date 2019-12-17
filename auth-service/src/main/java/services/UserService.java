package services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import dtos.UserDTO;
import models.User;
import repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private BCryptPasswordEncoder encoder;
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	public UserDTO getById(int id) {
		return userRepository.findById(id);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
	public Page<User> getPage(Pageable page) {
		return userRepository.findAll(page);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean create(User user) {
		// Encrypt password and save new user
		String encryptedPassword = encoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		user = userRepository.save(user);

		return user != null;
	}

	public UserDTO login(String username, String password) {
		User user = userRepository.findByUsername(username);
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
