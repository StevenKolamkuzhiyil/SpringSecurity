package com.stevenkolamkuzhiyil.TicTacToe.exception.throwable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(@NotEmpty @Email String email) {
        super(String.format("User with email %s already exists", email));
    }
}
