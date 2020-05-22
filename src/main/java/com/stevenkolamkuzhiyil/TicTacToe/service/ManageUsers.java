package com.stevenkolamkuzhiyil.TicTacToe.service;

import com.stevenkolamkuzhiyil.TicTacToe.model.User;

import javax.validation.constraints.NotEmpty;

public interface ManageUsers {
    User getUserByEmail(@NotEmpty String email);
}
