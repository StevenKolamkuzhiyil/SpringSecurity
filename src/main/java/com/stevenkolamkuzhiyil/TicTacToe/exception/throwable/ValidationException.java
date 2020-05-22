package com.stevenkolamkuzhiyil.TicTacToe.exception.throwable;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {

    BindingResult bindingResult;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public ValidationException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return this.bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
