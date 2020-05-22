package com.stevenkolamkuzhiyil.TicTacToe.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InvalidObjectException extends ApiException {

    InvalidObject object;

    public InvalidObjectException(ZonedDateTime timestamp, HttpStatus httpStatus,
                                  String message, String path, String redirectURL, BindingResult bindingResult) {
        super(timestamp, httpStatus, message, path, redirectURL);

        object = new InvalidObject();
        object.setName(bindingResult.getObjectName());
        object.setErrors(bindingResult.getGlobalErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));

        Map<String, String> fieldErrors = new HashMap<>();
        bindingResult.getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        object.setFieldErrors(fieldErrors);
    }

    public InvalidObject getObject() {
        return object;
    }

    public void setObject(InvalidObject object) {
        this.object = object;
    }
}
