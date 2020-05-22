package com.stevenkolamkuzhiyil.TicTacToe.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private final ZonedDateTime timestamp;
    private final String error;
    private final Integer status;
    private final String message;
    private final String path;
    private final String redirectURL;

    public ApiException(ZonedDateTime timestamp, HttpStatus httpStatus, String message, String path, String redirectURL) {
        this.timestamp = timestamp;
        this.error = httpStatus.name();
        this.status = httpStatus.value();
        this.message = message;
        this.path = path;
        this.redirectURL = redirectURL;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getRedirectURL() {
        return redirectURL;
    }
}
