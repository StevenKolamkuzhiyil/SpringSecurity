package com.stevenkolamkuzhiyil.TicTacToe.exception.throwable;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("Invalid Credentials");
    }

    public InvalidCredentialException(String message) {
        super(message);
    }
}
