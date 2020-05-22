package com.stevenkolamkuzhiyil.TicTacToe.service;

import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.UserNotFoundException;
import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import com.stevenkolamkuzhiyil.TicTacToe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ManageUsersImpl implements ManageUsers {

    protected final UserRepository userRepository;

    @Autowired
    public ManageUsersImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
