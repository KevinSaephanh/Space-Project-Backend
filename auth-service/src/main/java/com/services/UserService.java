package com.services;

import com.dtos.LoginDTO;
import com.dtos.UserDTO;
import com.exceptions.BadRequestException;
import com.exceptions.UserCreationException;
import com.exceptions.UserNotFoundException;
import com.filters.JwtToken;
import com.models.User;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        if (id <= 0)
            throw new BadRequestException("Invalid id value provided");

        // Attempt to find user by id
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("No user found with provided id");
        return user.get();
    }

    public UserDTO addUser(UserDTO user) {
        // Check if passwords match
        if (!user.getPassword().equals(user.getMatchingPassword()))
            throw new UserCreationException("Passwords do not match!");

        // Encrypt password and create new User
        String encrypedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encrypedPassword);
        newUser.setRole("USER");

        userRepository.save(newUser);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.equals(""))
            throw new BadRequestException("Invalid username value provided");

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("No user found with provided username");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    @Transactional(readOnly = true)
    public String login(LoginDTO user) throws Exception {
        // Attempt to authenticate user
        authenticate(user.getUsername(), user.getPassword());

        final UserDetails retrievedUser = loadUserByUsername(user.getUsername());
        return jwtToken.generateToken(retrievedUser);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateUser(User updatedUser, User user) {
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteUserById(int id) {
        if (id <= 0)
            throw new BadRequestException("Invalid id value provided");

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("No user found with provided id");

        userRepository.delete(user.get());
        return true;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID CREDENTIALS", e);
        }

    }
}