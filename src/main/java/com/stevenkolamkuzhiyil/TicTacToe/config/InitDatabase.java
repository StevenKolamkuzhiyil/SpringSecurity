package com.stevenkolamkuzhiyil.TicTacToe.config;

import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import com.stevenkolamkuzhiyil.TicTacToe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class InitDatabase implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param args The command line arguments.
     * @brief A command line runner which initializes the database with default data.
     */
    @Override
    public void run(String... args) {
        userRepository.deleteAll();

        String password = "password";
        String encodedPassword1 = passwordEncoder.encode(password);
        String encodedPassword2 = passwordEncoder.encode(password);
        String encodedPassword3 = passwordEncoder.encode(password);

        User user1 = new User("admin@email.com", "admin", 0, encodedPassword1, "ADMIN", "");
        User user2 = new User("user@email.com", "user", 0, encodedPassword2, "USER", "");
        User user3 = new User("user1@email.com", "user", 1, encodedPassword3, "USER", "");

        List<User> users = Arrays.asList(user1, user2, user3);

        userRepository.saveAll(users);

    }
}
