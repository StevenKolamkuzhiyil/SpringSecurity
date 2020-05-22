package com.stevenkolamkuzhiyil.TicTacToe.service;

import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.InvalidCredentialException;
import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.UserAlreadyExistsException;
import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.UserNotFoundException;
import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import com.stevenkolamkuzhiyil.TicTacToe.model.form.NewUserDTO;
import com.stevenkolamkuzhiyil.TicTacToe.model.form.UserProfileDTO;
import com.stevenkolamkuzhiyil.TicTacToe.repository.UserRepository;
import com.stevenkolamkuzhiyil.TicTacToe.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserService extends ManageUsersImpl {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUserAccount(NewUserDTO newUserDTO) {
        try {
            User user = getUserByEmail(newUserDTO.getEmail());
            throw new UserAlreadyExistsException(newUserDTO.getEmail());
        } catch (UserNotFoundException ex) {
            User user = new User();
            user.setUsername(newUserDTO.getUsername());
            user.setUsernameNr(getUsernameCount(user.getUsername()));
            user.setEmail(newUserDTO.getEmail());
            user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
            user.setEnabled(true);
            user.setRoles("USER");
            user.setPermissions("");
            userRepository.save(user);
        }
    }

    public User updateUserDetails(UserProfileDTO userProfileDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(userProfileDTO.getCurrentPassword(), userDetails.getPassword())) {
            throw new InvalidCredentialException("Enter your password to confirm changes.");
        }

        User user = getUserByEmail(userDetails.getUsername());
        updateUserDetails(user, userProfileDTO);
        userDetails.setUsername(user.getEmail());
        userDetails.setPassword(user.getPassword());
        return user;
    }

    private void updateUserDetails(User user, UserProfileDTO userProfileDTO) {
        if (!user.getUsername().equals(userProfileDTO.getUsername())) {
            user.setUsername(userProfileDTO.getUsername());
            user.setUsernameNr(getUsernameCount(user.getUsername()));
        }

        if (!user.getEmail().equals(userProfileDTO.getEmail())) {
            try {
                User existingUser = getUserByEmail(userProfileDTO.getEmail());
                throw new UserAlreadyExistsException(userProfileDTO.getEmail());
            } catch (UserNotFoundException ex) {
                user.setEmail(userProfileDTO.getEmail());
            }
        }

        if (userProfileDTO.getPassword() != null && !userProfileDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
        }

        userRepository.save(user);
    }

    // TODO: delete user
    //

    private int getUsernameCount(String username) {
        Set<Integer> numbers = userRepository.findAllByUsername(username)
                .stream()
                .map(User::getUsernameNr)
                .collect(Collectors.toSet());

        return IntStream.range(0, numbers.size())
                .filter(n -> !numbers.contains(n))
                .findFirst().orElse(numbers.size());
    }
}
