package com.services;

import com.exceptions.BadRequestException;
import com.exceptions.UserNotFoundException;
import com.models.User;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id) {
        if (id <= 0)
            throw new BadRequestException("Invalid id value provided");

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("No user found with provided id");
        return user.get();
    }

    @Override
    public User addUser(User user) {
        String encrypedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encrypedPassword);
        userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        if (username == null || username.equals(""))
            throw new BadRequestException("Invalid username value provided");

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException("No user found with provided username");
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateUser(User updatedUser, User user) {
        return true;
    }

    @Override
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
}