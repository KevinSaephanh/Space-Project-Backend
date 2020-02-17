package com.services;

import com.models.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(int id);

    User addUser(User user);

    User findByUsername(String username);

    boolean updateUser(User updatedUser, User user);

    boolean deleteUserById(int id);
}