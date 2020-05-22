package com.stevenkolamkuzhiyil.TicTacToe.repository;

import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    List<User> findAllByUsername(String username);
}
