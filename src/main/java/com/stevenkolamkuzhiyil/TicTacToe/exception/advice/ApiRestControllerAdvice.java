package com.stevenkolamkuzhiyil.TicTacToe.exception.advice;

import com.stevenkolamkuzhiyil.TicTacToe.exception.InvalidObjectException;
import com.stevenkolamkuzhiyil.TicTacToe.exception.throwable.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@RestControllerAdvice
public class ApiRestControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ResponseEntity<?> validationFailedHandler(ValidationException ex, HttpServletRequest request) {
        InvalidObjectException invalidObjectException = new InvalidObjectException(ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request.getRequestURI(),
                null,
                ex.getBindingResult());

        return ResponseEntity.badRequest().body(invalidObjectException);
    }

}
