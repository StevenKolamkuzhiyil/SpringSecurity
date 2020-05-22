package com.stevenkolamkuzhiyil.TicTacToe.exception.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such User")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(@NotEmpty @Email String email) {
        super(String.format("User with email %s not found", email));
    }

}
