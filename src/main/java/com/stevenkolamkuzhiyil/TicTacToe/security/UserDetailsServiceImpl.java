package com.stevenkolamkuzhiyil.TicTacToe.security;

import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.UserNotFoundException;
import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import com.stevenkolamkuzhiyil.TicTacToe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((new UserNotFoundException(email)).getMessage()));

        return new UserDetailsImpl(user);
    }
}
